
package org.usfirst.frc.team3130.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3130.robot.commands.ResetSolenoids;
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
	public static BasicCANTalon btHopper;
	public static BasicCANTalon btGearBar;
	public static BasicCANTalon btIntake;
	public static BasicCANTalon btLeftIndex;
	public static BasicCANTalon btRightIndex;
	
	private static ResetSolenoids resetGear;
	
	@Override
	public void robotInit() {
		robotSensors = new RobotSensors();
		robotSensors.start();

		bcGearPinch = new BasicCylinder(RobotMap.PNM_GEARPINCH);
		bcGearLift = new BasicCylinder(RobotMap.PNM_GEARLIFT);
		
		btHopper = new BasicCANTalon(RobotMap.CAN_HOPPERSTIR);
		btGearBar = new BasicCANTalon(RobotMap.CAN_GEARBAR);
		btIntake = new BasicCANTalon(RobotMap.CAN_INTAKEMOTOR);
		btLeftIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORLEFT);
		btRightIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORRIGHT);

		resetGear = new ResetSolenoids();
		
		OI.GetInstance();
		Chassis.GetInstance();
		Climber.GetInstance();
		JetsonInterface.GetInstance();
		ShooterWheelsLeft.GetInstance();
		ShooterWheelsRight.GetInstance();
		WheelSpeedCalculationsRight.GetInstance();
		WheelSpeedCalculationsLeft.GetInstance();

		chooser = new SendableChooser<CommandGroup>();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	
	@Override
	public void disabledInit() {
		resetGear.start();
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
