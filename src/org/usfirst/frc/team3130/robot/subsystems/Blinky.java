package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.commands.BlinkyController;

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
	private static String currCom = "S";
	
	private static Blinky m_pInstance;
	public static Blinky GetInstance(){
		if(m_pInstance == null) m_pInstance = new Blinky();
    	return m_pInstance;
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new BlinkyController());
    }
	
	public Blinky(){
		comms = new SerialPort(57600, SerialPort.Port.kUSB);
	}
	
	public static void runLights(String command){
		if(command != currCom){
		comms.writeString(command);
		System.out.print("Blinky" + command);
		currCom = command;
		return;
		}
	}
}

