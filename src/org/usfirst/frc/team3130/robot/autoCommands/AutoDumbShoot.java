package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.IndexMotorLeft;
import org.usfirst.frc.team3130.robot.subsystems.IndexMotorRight;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDumbShoot extends Command {

	private double percentage;
	
    public AutoDumbShoot() {
        requires(IndexMotorLeft.GetInstance());
        requires(IndexMotorRight.GetInstance());
    }

    /**
     * The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the index motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public void setParam(double percent)
    {
    	percentage = percent;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	IndexMotorLeft.driveIndexMotor(percentage);
    	IndexMotorRight.driveIndexMotor(percentage);
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
    	IndexMotorLeft.driveIndexMotor(0);
    	IndexMotorRight.driveIndexMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
