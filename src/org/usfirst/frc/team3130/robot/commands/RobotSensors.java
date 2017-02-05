package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RobotSensors extends Command {

    public RobotSensors() {
    	//Ensure permanent running
    	this.setRunWhenDisabled(true);
    	this.setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("TEST", 100);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Shooter Wheel
    	//SmartDashboard.putNumber("Shooter Wheel Speed", ShooterWheels.getSpeed());
    	//SmartDashboard.putNumber("Shooter Wheel Voltage", ShooterWheels.GetVolt());
    	//SmartDashboard.putNumber("Shooter Wheel Current", ShooterWheels.GetCurrent());
    	
    	//Chassis
    	//SmartDashboard.putNumber("Front Left Wheel Speed", Chassis.GetSpeedL());
    	//SmartDashboard.putNumber("Front Right Wheel Speed", Chassis.GetSpeedR());
    	//SmartDashboard.putNumber("Front Left Voltage", Chassis.GetVoltL());
    	//SmartDashboard.putNumber("Front Right Voltage", Chassis.GetVoltR());
    	//SmartDashboard.putNumber("Front Left Current", Chassis.GetCurrentL());
    	//SmartDashboard.putNumber("Front Right Current", Chassis.GetCurrentR());
    	
    	//Shooter Altitude
    	//SmartDashboard.putNumber("Shooter Altitude Angle", ShooterAltitude.getAltitude());
    	
    	//Intake
    	//SmartDashboard.putNumber("Intake Wheels Speed", Intake.getIntake());
    	//SmartDashboard.putNumber("Intake Wheels Voltage", Intake.getVolt());
    	//SmartDashboard.putNumber("Intake Wheels Current", Intake.getCurrent());
    	
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
