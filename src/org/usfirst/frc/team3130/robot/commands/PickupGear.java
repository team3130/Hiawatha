package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupGear extends CommandGroup {

    public PickupGear() {
    	requires(Robot.bcGearLift);
    	requires(Robot.bcGearPinch);
    	
    	addSequential(new AutoBasicActuate(Robot.bcGearPinch, true), 0.1);
    	addSequential(new AutoBasicActuate(Robot.bcGearLift, false), 0.75);
    }
}
