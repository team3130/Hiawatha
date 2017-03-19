package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public class ContDrive extends ContinuousDrive{

	private double angle;
	
    public ContDrive() {
        super();
    }
    
    public ContDrive(ContinuousDrive previous)
    {
    	super(previous);
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
    	
    	if(prev!=null) angle=prev.getEndAngle();
    	else angle = Chassis.GetAngle() * (Math.PI/180f);
    	
    	Chassis.HoldAbsAngle(angle);
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

	@Override
	public double getEndAngle() {
		return angle;
	}

}
