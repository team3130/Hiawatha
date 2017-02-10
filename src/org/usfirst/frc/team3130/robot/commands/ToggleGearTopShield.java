package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleGearTopShield extends Command {

    public ToggleGearTopShield() {
        requires(Robot.bcGearShield);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.bcGearShield.actuate(true);
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
    	Robot.bcGearShield.actuate(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
