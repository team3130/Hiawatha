package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallIntakeIn extends Command {

    public BallIntakeIn() {
        requires(Robot.btIntakeL);
        requires(Robot.btIntakeR);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.btIntakeL.spinMotor(0.4);
    	Robot.btIntakeR.spinMotor(0.4);
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
    	Robot.btIntakeL.spinMotor(0);
    	Robot.btIntakeR.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
