
package org.usfirst.frc.team3130.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;
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
	SendableChooser<CommandGroup> chooser;
	RobotSensors robotSensors;

	public static BasicCylinder bcGearPinch;	//Disabled Open
	public static BasicCylinder bcGearLift;		//Disabled Up
	public static BasicCylinder bcGearDoors;	//Disabled Up
	public static BasicCylinder bcGearShield;	//Disabled In
	
	private static AutoBasicActuate gearDoorsDownEnable;
	
	@Override
	public void robotInit() {
		robotSensors = new RobotSensors();
		robotSensors.start();

		bcGearPinch = new BasicCylinder(RobotMap.PNM_GEARPINCH);
		bcGearLift = new BasicCylinder(RobotMap.PNM_GEARLIFT);
		bcGearDoors = new BasicCylinder(RobotMap.PNM_GEARDOOR);
		bcGearShield = new BasicCylinder(RobotMap.PNM_TOPGEARSHIELD);
		
		gearDoorsDownEnable = new AutoBasicActuate(bcGearDoors, true);
		
		OI.GetInstance();
		Chassis.GetInstance();
		Climber.GetInstance();
		Hopper.GetInstance();
		IndexMotorLeft.GetInstance();
		IndexMotorRight.GetInstance();
		Intake.GetInstance();
		ShooterWheelsLeft.GetInstance();
		ShooterWheelsRight.GetInstance();
		

		chooser = new SendableChooser<CommandGroup>();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		gearDoorsDownEnable.start();
		
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
		
		gearDoorsDownEnable.start();
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
