package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LowerGearPickup extends CommandGroup {

	private PickupGear backToDefault;
	
    public LowerGearPickup() {
    	requires(Robot.bcGearPinch);
    	requires(Robot.btGearBar);

    	backToDefault = new PickupGear();
    	
    	addParallel(new AutoBasicActuate(Robot.bcGearPinch, false), 0.1);
    	addParallel(new BasicSpinMotor(Robot.btGearBar, .8));	//TODO: Determine Polarity
    }
    
    @Override
    protected void end()
    {
    	backToDefault.start();
    }
    
    @Override
    protected void interrupted()
    {
    	end();
    }
	
	@Override
	protected boolean isFinished()
	{
		return false;
	}
}
