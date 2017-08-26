package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;


import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpeedCurveShoot extends Command {

    public SpeedCurveShoot() {
        requires(Robot.wscRight);
        requires(Robot.wscLeft);
        //requires(ShooterWheelsLeft.GetInstance());
        //requires(ShooterWheelsRight.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Testing");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double dist = Preferences.getInstance().getDouble("Distance", 120);
    	//ShooterWheelsLeft.setSpeed(Robot.wscLeft.GetSpeed(dist));
    	//ShooterWheelsRight.setSpeed(Robot.wscRight.GetSpeed(dist));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//ShooterWheelsLeft.stop();
    	//ShooterWheelsRight.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
