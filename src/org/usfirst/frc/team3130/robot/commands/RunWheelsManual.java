package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.*;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunWheelsManual extends Command {

    public RunWheelsManual() {
        requires(ShooterWheelsLeft.GetInstance());
        requires(ShooterWheelsRight.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double leftSpeed = Preferences.getInstance().getDouble("Left Speed Setpoint", 1500);
    	double rightSpeed = Preferences.getInstance().getDouble("Right Speed Setpoint", 1500);
    	
    	ShooterWheelsLeft.setPID();
    	ShooterWheelsLeft.setSpeed(leftSpeed);

    	ShooterWheelsRight.setPID();
    	ShooterWheelsRight.setSpeed(rightSpeed);

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
    	ShooterWheelsLeft.stop();
    	ShooterWheelsRight.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
