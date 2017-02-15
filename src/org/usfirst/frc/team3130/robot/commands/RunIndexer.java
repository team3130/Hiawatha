package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.*;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunIndexer extends Command {

    public RunIndexer() {
        requires(IndexMotorLeft.GetInstance());
        requires(IndexMotorRight.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	IndexMotorLeft.driveIndexMotor(Preferences.getInstance().getDouble("Index Motor PercentVBus", 0.2));
    	IndexMotorRight.driveIndexMotor(Preferences.getInstance().getDouble("Index Motor PercentVBus", 0.2));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	IndexMotorLeft.driveIndexMotor(0);
    	IndexMotorRight.driveIndexMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
