package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutoDelay extends Command {

    public AutoDelay() {
        requires(Chassis.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.DriveArcade(0, 0);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
