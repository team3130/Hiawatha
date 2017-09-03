package org.usfirst.frc.team3130.robot.vision;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * AdbBridge interfaces to an Android Debug Bridge (adb) binary, which is needed
 * to communicate to Android devices over USB.
 *
 * adb binary provided by https://github.com/Spectrum3847/RIOdroid
 */
public class AdbBridge {
	boolean EnableAllAdb;
    Path bin_location_;
    public final static Path DEFAULT_LOCATION = Paths.get("/usr/bin/adb");
    String adbState = "uninitialized";
    public AdbBridge() {
    	EnableAllAdb = true; //If you want to debug adb on the RoboRio, set this to false (prevents adb server from starting and restarting, this can be useful if the phone doesn't show adb authorization prompt) 
        Path adb_location;
        String env_val = System.getenv("FRC_ADB_LOCATION");
        if (env_val == null || "".equals(env_val)) {
            adb_location = DEFAULT_LOCATION;
        } else {
            adb_location = Paths.get(env_val);
        }
        bin_location_ = adb_location;
        adbState = " AdbBridge() Enabled: "+String.valueOf(EnableAllAdb);
        System.out.println(adbState);  
    }
    //killAdb() used during testing, Adb server shouldn't be killed on Init, doing so invalidates Rio Adb authentication
    /*private boolean killAdb(){
    	Runtime r = Runtime.getRuntime();
        String cmd = "/usr/bin/killall adb";
        try {
        	//if(EnableAllAdb == true)
        	{
        		System.out.println("ADB kill: " + cmd);
        		Process p = r.exec(cmd);
        		int code = p.waitFor();
        		System.out.println("ADB kill code: " + String.valueOf(code));
        	}
        } catch (IOException e) {
            System.err.println("AdbBridge: Could not run command " + cmd);
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            System.err.println("AdbBridge: Could not run command " + cmd);
            e.printStackTrace();
            return false;
        }
        return true;	
    }*/
    public AdbBridge(Path location) {
        bin_location_ = location;
    }

    private boolean runCommand(String args) {
        Runtime r = Runtime.getRuntime();
        String cmd = bin_location_.toString() + " " + args;
        try {
        	if(EnableAllAdb == true){
        		System.out.println("ADB cmd: " + cmd);
        		Process p = r.exec(cmd);
        		int code = p.waitFor();
        		System.out.println("ADB cmd code: " + String.valueOf(code));
        	}
        } catch (IOException e) {
            System.err.println("AdbBridge: Could not run command " + cmd);
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            System.err.println("AdbBridge: Could not run command " + cmd);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void start() {
    	adbState = " Adb start() ";
        System.out.println(adbState);
        runCommand("start-server");
        /*try {
        	Thread.sleep(3000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        } */
    }

    public void stop() {
    	adbState = " Adb stop() ";
        System.out.println("Stopping adb");
        runCommand("kill-server");
    }

    public void restartAdb() {
        System.out.println("Restarting adb");
        stop();
        start();
    }

    public void portForward(int local_port, int remote_port) {
    	adbState = " Adb PortForward " + local_port + " rport: " + remote_port;
        runCommand("forward tcp:" + local_port + " tcp:" + remote_port);
    }

    public void reversePortForward(int remote_port, int local_port) {
    	adbState = "Adb rPortForward " + local_port + " rport: " + remote_port;
        runCommand("reverse tcp:" + remote_port + " tcp:" + local_port);
    }

    public void restartApp() {
    	adbState = "Adb restartApp()";
        System.out.println("Restarting app");
        runCommand("shell am force-stop com.team3130.vision3130 \\; "
                + "am start com.team3130.vision3130/com.team3130.vision3130.VisionTrackerActivity");
        System.out.println("App started");
    }
    
    public void stopApp() {
    	adbState = "Adb stopApp()";
    	runCommand("shell am force-stop com.team3130.vision3130");
    }

    public void startApp() {
    	adbState = "Adb startApp()";
    	System.out.println("Starting app");
    	runCommand("shell am start com.team3130.vision3130/com.team3130.vision3130.VisionTrackerActivity");
    	System.out.println("App started");
    }
    public String getAdbState(){
    	return adbState;
    }
}
