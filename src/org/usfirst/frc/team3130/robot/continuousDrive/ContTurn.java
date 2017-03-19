package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public abstract class ContTurn extends ContinuousDrive{

	boolean leftOutside;
	
    public ContTurn() {
        super();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(leftOutside){
    		Chassis.DriveTank(speed, 0);
    	}else{
    		Chassis.DriveTank(0, speed);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	super.end();
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	public void SetParam(double percentVBus, double angle) {
		super.SetParam(percentVBus, angle);
		if(angle<0) leftOutside = false;
		else leftOutside = true;
	}
}
