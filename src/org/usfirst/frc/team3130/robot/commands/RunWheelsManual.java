package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunWheelsManual extends Command {

    public RunWheelsManual() {
        requires(ShooterWheelsLeft.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double testSpeed = Preferences.getInstance().getDouble("Speed Setpoint", 3800);
    	System.out.println(testSpeed);
    	ShooterWheelsLeft.setSpeed(testSpeed);
    	ShooterWheelsLeft.setPID();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
