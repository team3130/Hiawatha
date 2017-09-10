package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.*;
import org.usfirst.frc.team3130.robot.vision.VisionServer;
import org.usfirst.frc.team3130.robot.vision.VisionUpdate;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class RobotSensors extends Command {
	
	Timer timer = new Timer();
	
	boolean measuring = false;
	
    public RobotSensors() {
    	//Ensure permanent running
    	this.setRunWhenDisabled(true);
    	this.setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//SmartDashboard.putNumber("Recovery Time", 0.0);
    	timer.reset();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//SmartDashboard.putNumber("Left Index Current", Robot.btLeftIndex.getCurrent());
    	//SmartDashboard.putNumber("Right Index Current", Robot.btRightIndex.getCurrent());
    	
    	//SmartDashboard.putNumber("Shooter Left Wheel Speed", ShooterWheelsLeft.getSpeed());
    	//SmartDashboard.putNumber("Shooter Left Wheel Setpoint", ShooterWheelsLeft.GetSetpoint());
    	//SmartDashboard.putNumber("Shooter Left Wheel Voltage", ShooterWheelsLeft.GetVolt());
    	//SmartDashboard.putNumber("Shooter Left Wheel Position", ShooterWheelsLeft.GetPosition());
    	//SmartDashboard.putNumber("Shooter Left Wheel Current", ShooterWheelsLeft.GetCurrent());

    	//SmartDashboard.putNumber("Shooter Right Wheel Speed", ShooterWheelsRight.getSpeed());
    	//SmartDashboard.putNumber("Shooter Right Wheel Setpoint", ShooterWheelsRight.GetSetpoint());
    	//SmartDashboard.putNumber("Shooter Right Wheel Voltage", ShooterWheelsRight.GetVolt());
    	//SmartDashboard.putNumber("Shooter Right Wheel Position", ShooterWheelsRight.GetPosition());
    	//SmartDashboard.putNumber("Shooter Right Wheel Current", ShooterWheelsRight.GetCurrent());
        
        TurretFlywheel.outputToSmartDashboard();
        VisionServer.outputToSmartDashboard();
        AndroidInterface.outputToSmartDashboard();
        TurretAngle.outputToSmartDashboard();
        
    	/*
    	SmartDashboard.putNumber("Flywheel Speed", TurretFlywheel.getSpeed());
    	SmartDashboard.putBoolean("Turret Wheel Up to Speed", TurretFlywheel.isOnTarget());
    	SmartDashboard.putNumber("Turret Wheel Setpoint", TurretFlywheel.getSetpoint());
    	*/
        
    	SmartDashboard.putInt("Target Count", VisionUpdate.hasTargetInfo());
    	
    	
    	//Chassis
    	//SmartDashboard.putNumber("Front Left Wheel Speed", Chassis.GetSpeedL());
    	//SmartDashboard.putNumber("Front Right Wheel Speed", Chassis.GetSpeedR());
    	//SmartDashboard.putNumber("Front Left Voltage", Chassis.GetFrontVoltL());
    	//SmartDashboard.putNumber("Front Right Voltage", Chassis.GetFrontVoltR());
    	//SmartDashboard.putNumber("Rear Left Voltage", Chassis.GetRearVoltL());
    	//SmartDashboard.putNumber("Rear Right Voltage", Chassis.GetRearVoltR());
    	//SmartDashboard.putNumber("Front Left Current", Chassis.GetFrontCurrentL());
    	//SmartDashboard.putNumber("Front Right Current", Chassis.GetFrontCurrentR());
    	//SmartDashboard.putNumber("Rear Left Current", Chassis.GetRearCurrentL());
    	//SmartDashboard.putNumber("Rear Right Current", Chassis.GetRearCurrentR());
    	//SmartDashboard.putNumber("Angle", Chassis.GetAngle());
    	//SmartDashboard.putNumber("Left Distance", Chassis.GetDistanceL());
    	//SmartDashboard.putNumber("Right Distance", Chassis.GetDistanceR());
    	
    	
    	//Shooter Altitude
    	//SmartDashboard.putNumber("Shooter Altitude Angle", ShooterAltitude.getAltitude());
    	
    	//Intake
    	//SmartDashboard.putNumber("Intake Wheels Speed", Intake.getIntake());
    	//SmartDashboard.putNumber("Intake Wheels Voltage", Intake.getVolt());
    	//SmartDashboard.putNumber("Intake Wheels Current", Intake.getCurrent());
    	
    	//Gear
    	//SmartDashboard.putBoolean("Gear Lift State", Robot.bcGearLift.getState());
    	//SmartDashboard.putBoolean("Gear Pinch State", Robot.bcGearPinch.getState());
    	
    	//SmartDashboard.putBoolean("GearShift State", !Chassis.GetShiftedDown());
    	
    	/*if (Math.abs(JetsonInterface.getDouble("Peg Sys Time", 0) - JetsonInterface.getDouble("Peg Time", 9999)) < .15) {
    		SmartDashboard.putBoolean("Gear Target Acquired", true);
    	}
    	else {
    		SmartDashboard.putBoolean("Gear Target Acquired", false);
    	}
    	*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
