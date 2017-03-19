package org.usfirst.frc.team3130.robot.continuousDrive;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.command.Command;

public abstract class ContinuousDrive extends Command {

	double valPrev = 0;
	double speed = 0;
	double valEnd = 0;
	double valFinal = 0;
	
	boolean heldAngle = false;
	boolean done = false;
	
	ContinuousDrive prev = null;
	
	public ContinuousDrive() {
		requires(Chassis.GetInstance());
	}
	
	public ContinuousDrive(ContinuousDrive previousCommand){
		requires(Chassis.GetInstance());
		prev = previousCommand;
	}
	
	protected abstract double getPos();
	
	@Override
	protected void initialize()
	{
		done = false;
		heldAngle = false;
		Chassis.ReleaseAngle();
		Chassis.Shift(false);
		Chassis.TalonsToCoast(false);
		Chassis.setTurnDir(TurnDirection.kStraight);
    	valPrev = getPos();
	}
	
	@Override
	protected void end()
	{
    	valFinal = getPos();
    	done = true;
	}
	
	@Override
	protected boolean isFinished()
	{
        if(valEnd < 0){
        	return getPos() <= valPrev + valEnd;
        }else{
        	return getPos() >= valPrev + valEnd;
        }
	}
	
	/**
	 * Sets up the drive required for a ContinuousDrive
	 * <p>Defines a move amount and a speed at which to move</p>
	 * @param percentVBus the percentVBus at which to drive
	 * @param endVal the value to move
	 */
	void SetParam(double percentVBus, double endVal)
	{
		speed=percentVBus;
		valEnd = endVal;
	}
	
	/**
	 * Gets the value by how much it overshot
	 * @return the ammount the robot overshot its move
	 */
	public double GetOvershoot()
	{
		return Math.abs(valPrev+(done?valFinal:getPos())) - Math.abs(valEnd);
	}
	
	public abstract double getEndAngle();
}
