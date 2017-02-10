package org.usfirst.frc.team3130.robot.commands;


import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActuateGearDoors extends Command {

    public ActuateGearDoors() {
    	requires(Robot.bcGearDoors);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.bcGearDoors.actuate(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.bcGearDoors.actuate(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
