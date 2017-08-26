package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
//TODO:TurretFlywheel needs work
public class RunWheelsManual extends Command {

	private double leftSpeed;
	private double rightSpeed;
	
    public RunWheelsManual() {

    }

    public void setParam(double lSpeed, double rSpeed){
    	leftSpeed = lSpeed;
    	rightSpeed = rSpeed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	//ShooterWheelsLeft.setPID();
    	//ShooterWheelsLeft.setSpeed(leftSpeed);

    	//ShooterWheelsRight.setPID();
    	//ShooterWheelsRight.setSpeed(rightSpeed);

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
    	//ShooterWheelsLeft.stop();
    	//ShooterWheelsRight.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
