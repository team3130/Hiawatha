package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSmartShoot extends Command {

	private double percentage;
	private CameraAim aimer;
	
    public AutoSmartShoot() {
        requires(Robot.btLeftIndex);
        requires(Robot.btRightIndex);
    }

    /**
     * The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the index motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public void setParam(double percent, CameraAim aim)
    {
    	percentage = percent;
    	aimer = aim;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(aimer.onTarget()){
    		Robot.btLeftIndex.spinMotor(percentage);
        	Robot.btRightIndex.spinMotor(percentage);
    	}else{
    		Robot.btLeftIndex.spinMotor(0);
    		Robot.btLeftIndex.spinMotor(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.btLeftIndex.spinMotor(0);
    	Robot.btRightIndex.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
