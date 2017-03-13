
package org.usfirst.frc.team3130.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;
import org.usfirst.frc.team3130.robot.autoCommands.DumbGearAuto;
import org.usfirst.frc.team3130.robot.autoCommands.GearAnd10;
import org.usfirst.frc.team3130.robot.commands.RobotSensors;
import org.usfirst.frc.team3130.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	SendableChooser<String> chooser;
	RobotSensors robotSensors;

	public static BasicCylinder bcGearPinch;	//Disabled Open
	public static BasicCylinder bcGearLift;		//Disabled Up
	public static BasicCANTalon btHopper;
	public static BasicCANTalon btGearBar;
	public static BasicCANTalon btIntake;
	public static BasicCANTalon btLeftIndex;
	public static BasicCANTalon btRightIndex;

	
	@Override
	public void robotInit() {
		robotSensors = new RobotSensors();
		robotSensors.start();

		bcGearPinch = new BasicCylinder(RobotMap.PNM_GEARPINCH, "Gear", "Pinch Cylinder");
		bcGearLift = new BasicCylinder(RobotMap.PNM_GEARLIFT, "Gear", "Lift Cylinder");
		
		btHopper = new BasicCANTalon(RobotMap.CAN_HOPPERSTIR, "Hopper", "Hopper Motor");
		btGearBar = new BasicCANTalon(RobotMap.CAN_GEARBAR, "Gear", "Gear Bar");
		btIntake = new BasicCANTalon(RobotMap.CAN_INTAKEMOTOR, "Intake", "Intake Motor");
		btLeftIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORLEFT, "Indexer", "Left Index Motor");
		btRightIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORRIGHT, "Indexer", "Right Index Motor");

			
		OI.GetInstance();
		Chassis.GetInstance();
		Climber.GetInstance();
		JetsonInterface.GetInstance();
		ShooterWheelsLeft.GetInstance();
		ShooterWheelsRight.GetInstance();
		Blinky.GetInstance();

		WheelSpeedCalculationsRight.GetInstance();
		WheelSpeedCalculationsLeft.GetInstance();


		// Simplest camera feed. Remove if not needed.
		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
		camera1.setResolution(320, 480);
		
		chooser = new SendableChooser<String>();
		chooser.addDefault("No Auton", "No Auto");
		chooser.addObject("Dumb Gear", "Dumb Gear Auto");
		chooser.addObject("Gear and 10", "Gear and 10");
		SmartDashboard.putData("Auto mode", chooser);
	}

	
	@Override
	public void disabledInit() {
		//resetGear.start();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	@Override
	public void autonomousInit() {
		
		switch(chooser.getSelected()){
			case "Dumb Gear Auto":
				autonomousCommand = new DumbGearAuto();
				break;
			case "No Auton":
				autonomousCommand = new AutoBasicActuate(bcGearPinch, true);
			case "Gear and 10":
				autonomousCommand = new GearAnd10();
				break;
			default:
				autonomousCommand = null;
		}
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
