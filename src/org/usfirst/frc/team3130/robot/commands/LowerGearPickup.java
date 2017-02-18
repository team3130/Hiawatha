package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.AutoBasicActuate;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LowerGearPickup extends CommandGroup {

    public LowerGearPickup() {
    	requires(Robot.bcGearDoors);
    	requires(Robot.bcGearLift);
    	requires(Robot.bcGearPinch);
    	
    	addParallel(new AutoBasicActuate(Robot.bcGearDoors, false), 0.1);
    	addSequential(new AutoBasicActuate(Robot.bcGearPinch, true), 0.1);
    	addSequential(new AutoBasicActuate(Robot.bcGearLift, true), 0.1);
    }
}
