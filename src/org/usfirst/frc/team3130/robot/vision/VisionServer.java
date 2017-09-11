package org.usfirst.frc.team3130.robot.vision;

import org.usfirst.frc.team3130.robot.Constants;

import org.usfirst.frc.team3130.robot.vision.messages.HeartbeatMessage;
import org.usfirst.frc.team3130.robot.vision.messages.OffWireMessage;
import org.usfirst.frc.team3130.robot.vision.messages.VisionMessage;
import org.usfirst.frc.team3130.util.CrashTrackingRunnable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This controls all vision actions, including vision updates, capture, and
 * interfacing with the Android phone with Android Debug Bridge. It also stores
 * all VisionUpdates (from the Android phone) and contains methods to add
 * to/prune the VisionUpdate list. Much like the subsystems, outside methods get
 * the VisionServer instance (there is only one VisionServer) instead of
 * creating new VisionServer instances.
 * 
 * @see VisionUpdate.java
 */

public class VisionServer extends CrashTrackingRunnable {

    private static VisionServer s_instance = null;
    private ServerSocket m_server_socket;
    private boolean m_running = true;
    private int m_port;
    private ArrayList<VisionUpdateReceiver> receivers = new ArrayList<>();
    AdbBridge adb = new AdbBridge();
    double lastMessageReceivedTime = 0;
    private boolean m_use_java_time = false;
    
    private AppMaintainanceThread m_maintainance;

    private ArrayList<ServerThread> serverThreads = new ArrayList<>();
    private volatile boolean mWantsAppRestart = false;
    private volatile boolean mWantsAppStart = false;
    
    public static VisionServer getInstance() {
        if (s_instance == null) {
            s_instance = new VisionServer(Constants.kAndroidAppTcpPort);
        }
        return s_instance;
    }

    private boolean mIsConnect = false;

    public boolean isConnected() {
        return mIsConnect;
    }

    public void requestAppRestart() {
        mWantsAppRestart = true;
    }
    
    public void requestAppStart() {
        mWantsAppStart = true;
    }

    protected class ServerThread extends CrashTrackingRunnable {
        private Socket m_socket;

        public ServerThread(Socket socket) {
            m_socket = socket;
        }

        public void send(VisionMessage message) {
            String toSend = message.toJson() + "\n";
            if (m_socket != null && m_socket.isConnected()) {
                try {
                    OutputStream os = m_socket.getOutputStream();
                    os.write(toSend.getBytes());
                } catch (IOException e) {
                    System.err.println("VisionServer: Could not send data to socket");
                }
            }
        }

        public void handleMessage(VisionMessage message, double timestamp) {
            if ("targets".equals(message.getType())) {
                VisionUpdate update = VisionUpdate.generateFromJsonString(timestamp, message.getMessage());
                receivers.removeAll(Collections.singleton(null));
                if (update.isValid()) {
                    for (VisionUpdateReceiver receiver : receivers) {
                        receiver.gotUpdate(update);
                    }
                }
            }
            if ("heartbeat".equals(message.getType())) {
                send(HeartbeatMessage.getInstance());
            }
        }

        public boolean isAlive() {
            return m_socket != null && m_socket.isConnected() && !m_socket.isClosed();
        }

        @Override
        public void runCrashTracked() {
            if (m_socket == null) {
                return;
            }
            try {
                InputStream is = m_socket.getInputStream();
                byte[] buffer = new byte[2048];
                int read;
                while (m_socket.isConnected() && (read = is.read(buffer)) != -1) {
                    double timestamp = getTimestamp();
                    lastMessageReceivedTime = timestamp;
                    String messageRaw = new String(buffer, 0, read);
                    String[] messages = messageRaw.split("\n");
                    for (String message : messages) {
                        OffWireMessage parsedMessage = new OffWireMessage(message);
                        if (parsedMessage.isValid()) {
                            handleMessage(parsedMessage, timestamp);
                        }
                    }
                }
                System.out.println("Socket disconnected");
            } catch (IOException e) {
                System.err.println("Could not talk to socket");
            }
            if (m_socket != null) {
                try {
                    m_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Instantializes the VisionServer and connects to ADB via the specified
     * port.
     * 
     * @param Port
     */
    private VisionServer(int port) {
        try {
        	System.out.println("Starting VisionServer");
            adb = new AdbBridge();
            m_port = port;
            m_server_socket = new ServerSocket(port);
            adb.start();
            adb.reversePortForward(port, port);
            try {
                String useJavaTime = System.getenv("USE_JAVA_TIME");
                m_use_java_time = "true".equals(useJavaTime);
            } catch (NullPointerException e) {
                m_use_java_time = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
        m_maintainance = new AppMaintainanceThread();
        new Thread(m_maintainance).start();
    }

    public void restartAdb() {
        adb.restartAdb();
        adb.reversePortForward(m_port, m_port);
    }
    
/*    public void shutdownVision() {
        m_maintainance.stop();
        adb.stopApp();
        adb.stop();
        
    } */

    /**
     * If a VisionUpdate object (i.e. a target) is not in the list, add it.
     * 
     * @see VisionUpdate
     */
    public void addVisionUpdateReceiver(VisionUpdateReceiver receiver) {
        if (!receivers.contains(receiver)) {
            receivers.add(receiver);
        }
    }

    public void removeVisionUpdateReceiver(VisionUpdateReceiver receiver) {
        if (receivers.contains(receiver)) {
            receivers.remove(receiver);
        }
    }

    @Override
    public void runCrashTracked() {
        while (m_running) {
            try {
                Socket p = m_server_socket.accept();
                ServerThread s = new ServerThread(p);
                new Thread(s).start();
                serverThreads.add(s);
            } catch (IOException e) {
                System.err.println("Issue accepting socket connection!");
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void outputToSmartDashboard() {
    	SmartDashboard.putString("adbState", VisionServer.getInstance().adb.getAdbState());
        SmartDashboard.putBoolean("Android_Connected", VisionServer.getInstance().isConnected());

    }

    private class AppMaintainanceThread extends CrashTrackingRunnable {
        private volatile boolean mVisionEnabled = true;
        @Override
        public void runCrashTracked() {
        	System.out.println("Starting AppMaintainanceThread");
            while (!Thread.interrupted()/*true*/) {
                if (mVisionEnabled == true) {
                    if (mWantsAppStart) {
                        adb.start();
                        adb.reversePortForward(m_port, m_port);
                        adb.startApp();
                        mWantsAppStart = false;
                    }else if (mWantsAppRestart) {
                        adb.start();
                        adb.reversePortForward(m_port, m_port);
                        adb.restartApp();
                        mWantsAppRestart = false;
                    } else {
                    	if (getTimestamp() - lastMessageReceivedTime > .1) {
                    		// camera disconnected
                    		adb.reversePortForward(m_port, m_port);
                    		mIsConnect = false;
                    	} else {
                    		mIsConnect = true;
                    	}
                    }
                }
                try {
                	Thread.sleep(200);
                } catch (InterruptedException e) {
                	e.printStackTrace();
                }
                
            }
        }
        
        public void stop() {
            mVisionEnabled = false;
        }
        
        public void restart() {
            mVisionEnabled = true;
        }
        
    }

    private double getTimestamp() {
        if (m_use_java_time) {
            return System.currentTimeMillis();
        } else {
            return Timer.getFPGATimestamp();
        }
    }
}
