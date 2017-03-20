package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 *
 */
public class ContTurnHeading extends ContTurn{
	
	public ContTurnHeading() {
		super();
	}

	protected double getPos()
	{
		return Chassis.GetAngle() * (Math.PI/180f);
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
		super.SetParam(percentVBus, angle);
	}

	@Override
	public double getEndAngle() {
		return valEnd + valPrev;
	}
}
