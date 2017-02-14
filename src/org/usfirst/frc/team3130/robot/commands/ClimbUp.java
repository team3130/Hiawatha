package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbUp extends Command {

	private int thresholdAmp = 5;
	
    public ClimbUp() {
        requires(Climber.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Climber.Climb(Preferences.getInstance().getDouble("Climber Up Speed", 1.0));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Climber.getLiftAmp() > thresholdAmp; //TODO: make it stop climbing when a certain amperage is reached
    }

    // Called once after isFinished returns true
    protected void end() {
    	Climber.Climb(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    end();
    }
}
