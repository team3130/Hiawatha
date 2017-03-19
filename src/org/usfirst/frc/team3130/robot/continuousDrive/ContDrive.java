package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public class ContDrive extends ContinuousDrive{

    public ContDrive() {
        super();
    }

	@Override
	public void SetParam(double percentVBus, double distance) {
		super.SetParam(percentVBus, distance);
	}
    
	@Override
	protected double getPos() {
		return Chassis.GetDistance();
	}
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    	Chassis.HoldAngle(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Chassis.DriveStraight(speed);
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

}
