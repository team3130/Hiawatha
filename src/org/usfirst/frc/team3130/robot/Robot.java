
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
import org.usfirst.frc.team3130.robot.autoCommands.SideGearDriveAuto;
import org.usfirst.frc.team3130.robot.autoCommands.SideGearHopperAuto;
import org.usfirst.frc.team3130.robot.autoCommands.VisionGearAnd10;
import org.usfirst.frc.team3130.robot.autoCommands.NoVisionGearAnd10;
import org.usfirst.frc.team3130.robot.autoCommands.VisionGearAuto;
import org.usfirst.frc.team3130.robot.autoCommands.FortyBallAuton;
import org.usfirst.frc.team3130.robot.autoCommands.NoVision40Ball;
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
	public static BasicCylinder bcHopperFloor;	//Disabled Down
	public static BasicCANTalon btHopper;
	public static BasicCANTalon btHopper2;
	public static BasicCANTalon btGearBar;
	public static BasicCANTalon btIntake;
	public static BasicCANTalon btLeftIndex;
	public static BasicCANTalon btRightIndex;
	public static WheelSpeedCalculations wscLeft;
	public static WheelSpeedCalculations wscRight;
	
	@Override
	public void robotInit() {
		robotSensors = new RobotSensors();
		robotSensors.start();

		bcGearPinch = new BasicCylinder(RobotMap.PNM_GEARPINCH, "Gear", "Pinch Cylinder");
		bcGearLift = new BasicCylinder(RobotMap.PNM_GEARLIFT, "Gear", "Lift Cylinder");
		bcHopperFloor = new BasicCylinder(RobotMap.PNM_HOPPERFLOOR, "Hopper", "Floor Cylinder");
		
		btHopper = new BasicCANTalon(RobotMap.CAN_HOPPERSTIR, "Hopper", "Hopper Motor");
		btHopper2 = new BasicCANTalon(RobotMap.CAN_HOPPER2, "Hopper", "Hopper2 Motor");
		btGearBar = new BasicCANTalon(RobotMap.CAN_GEARBAR, "Gear", "Gear Bar");
		btIntake = new BasicCANTalon(RobotMap.CAN_INTAKEMOTOR, "Intake", "Intake Motor");
		btLeftIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORLEFT, "Indexer", "Left Index Motor");
		btRightIndex = new BasicCANTalon(RobotMap.CAN_INDEXMOTORRIGHT, "Indexer", "Right Index Motor");

		wscLeft = new WheelSpeedCalculations("home/lvuser/speed-storage-left.ini");
		wscRight = new WheelSpeedCalculations("home/lvuser/speed-storage-right.ini");
			
		OI.GetInstance();
		Chassis.GetInstance();
		Climber.GetInstance();
		JetsonInterface.GetInstance();
		ShooterWheelsLeft.GetInstance();
		ShooterWheelsRight.GetInstance();
		Flashlight.GetInstance();

		// Simplest camera feed. Remove if not needed.
		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
		camera1.setResolution(320, 480);
		
		chooser = new SendableChooser<String>();
		chooser.addDefault("No Auton", "No Auto");
		chooser.addObject("Dumb Gear", "Dumb Gear Auto");
		chooser.addObject("Gear and 10", "Gear and 10");
		chooser.addObject("Vision Gear", "Vision Gear Auto");
		chooser.addObject("Vision Gear and 10", "Vision and 10");
		chooser.addObject("Forty Ball", "Forty Ball");
		chooser.addObject("Side Gear and Drive", "Gear and Drive");
		chooser.addObject("Side Gear Hopper", "Side Gear Hopper");
		chooser.addObject("No Vision Gear and 10", "No Vision Gear and 10");
		chooser.addObject("No Vision Forty Ball", "No Vision Forty Ball");
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
				break;
			case "Gear and 10":
				autonomousCommand = new GearAnd10();
				break;
			case "Vision Gear Auto":
				autonomousCommand = new VisionGearAuto();
				break;
			case "Vision and 10":
				autonomousCommand = new VisionGearAnd10();
				break;
			case "Forty Ball":
				autonomousCommand = new FortyBallAuton();
				break;
			case "Gear and Drive":
				autonomousCommand = new SideGearDriveAuto();
				break;
			case "Side Gear Hopper":
				autonomousCommand = new SideGearHopperAuto();
				break;
			case "No Vision Gear and 10":
				autonomousCommand = new NoVisionGearAnd10();
			    break;
			case "No Vision Forty Ball":
				autonomousCommand = new NoVision40Ball();
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
