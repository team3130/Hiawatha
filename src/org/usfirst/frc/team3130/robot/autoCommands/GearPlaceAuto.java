package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearPlaceAuto extends CommandGroup {

    public GearPlaceAuto() {
        requires(Chassis.GetInstance());
        requires(Robot.bcGearDoors);
        requires(Robot.bcGearPinch);
    }
}
