package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.IndexMotor;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunIndexer extends Command {

    public RunIndexer() {
        requires(IndexMotor.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double testSpeed = Preferences.getInstance().getDouble("Speed Setpoint", 2000);
    	System.out.println(testSpeed);
    	IndexMotor.setSpeed(testSpeed);
    	IndexMotor.setPID();
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
    	IndexMotor.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
