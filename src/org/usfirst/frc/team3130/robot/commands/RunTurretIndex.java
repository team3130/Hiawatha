package org.usfirst.frc.team3130.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3130.robot.Robot;

/**
 * Runs the turret's indexer
 */
public class RunTurretIndex extends Command {

    public RunTurretIndex() {
        requires(Robot.btTurretIndex);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.btTurretIndex.spinMotor(Preferences.getInstance().getDouble("Turret Index Motor PercentVBus", 0.2));
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
    	Robot.btTurretIndex.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
