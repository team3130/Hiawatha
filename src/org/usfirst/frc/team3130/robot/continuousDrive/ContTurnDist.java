package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public class ContTurnDist extends ContTurn{
	
    public ContTurnDist() {
        super();
    }

    protected double getPos()
    {
    	if(leftOutside) return Chassis.GetDistanceL();
    	return Chassis.GetDistanceR();
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	super.execute();
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
		super.SetParam(percentVBus, RobotMap.DIM_ROBOTWHEELTOWHEEL * angle);
		valEnd = Math.abs(valEnd);
	}
}
