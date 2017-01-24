package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.ShooterAltitude;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterAltitudeAdjust extends Command {

    public ShooterAltitudeAdjust() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ShooterAltitude.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Get the altitude goal from the preferences allowing for auton aiming and adjust accordingly.
    	double shooterAltitude = Preferences.getInstance().getDouble("Shooter Altitude", 0);
    	ShooterAltitude.setAltitude(shooterAltitude);
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
