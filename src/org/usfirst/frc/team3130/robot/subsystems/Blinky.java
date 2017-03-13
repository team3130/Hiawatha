package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.commands.BlinkyController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Blinky extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//Instance Handling
	
	private static SerialPort comms;
	private static Boolean arduinoConnected = false;
	private static String currCom = "S";
	private static boolean randLights = false;
	
	private static Blinky m_pInstance;
	public static Blinky GetInstance(){
		if(m_pInstance == null) m_pInstance = new Blinky();
    	return m_pInstance;
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new BlinkyController());
    }
	
	public Blinky(){
		try{
		comms = new SerialPort(57600, SerialPort.Port.kUSB);
		arduinoConnected = true;
		} catch(Exception ex){
			//If connection fails log the error and fall back to encoder based angles.
			String str_error = "Error instantiating Blinky Class from the Subsystem of the magically thing called robotics code: ";
			str_error += ex.getLocalizedMessage();
			DriverStation.reportError(str_error, true);
			comms = null;
			arduinoConnected = false;
		}
	}
	
	public static void runLights(String command){
		if(command != currCom && arduinoConnected){
		comms.writeString(command);
		System.out.print("Blinky" + command);
		currCom = command;
		return;
		}
	}
	
	public static void randomToggle(){
		if(randLights == true){
			randLights = false;
		}else randLights = true;
	}
	
	public static boolean random(){
		return randLights;
	}
}

