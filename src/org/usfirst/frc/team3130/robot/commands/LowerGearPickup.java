package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LowerGearPickup extends CommandGroup {

	private PickupGear backToDefault;
	
    public LowerGearPickup() {
    	requires(Robot.bcGearPinch);
    	requires(Robot.bcGearLift);
    	requires(Robot.btGearBar);

    	backToDefault = new PickupGear();
    	
    	addParallel(new AutoBasicActuate(Robot.bcGearPinch, false), 0.1);
    	addParallel(new BasicSpinMotor(Robot.btGearBar, Preferences.getInstance().getDouble("Gear Motor PercentVBus", .8)));	//TODO: Determine Polarity
    	addSequential(new AutoBasicActuate(Robot.bcGearLift, true), 0.1);
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
