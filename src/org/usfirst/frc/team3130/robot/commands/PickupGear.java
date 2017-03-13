package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupGear extends CommandGroup {

    public PickupGear() {
    	requires(Robot.bcGearPinch);
    	requires(Robot.btGearBar);
    	requires(Robot.bcGearLift);
    	
    	addSequential(new AutoBasicActuate(Robot.bcGearPinch, true), 0.1);
    	addParallel(new BasicSpinMotor(Robot.btGearBar, 0));
    	addSequential(new AutoBasicActuate(Robot.bcGearLift, false), 0.75);
    	
    }
}
