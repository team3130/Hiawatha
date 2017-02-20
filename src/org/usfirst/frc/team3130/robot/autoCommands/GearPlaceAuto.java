package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearPlaceAuto extends CommandGroup {
	
	private DriveToGear drive_toGear;;
	private AutoDriveStraightToPoint drive_ontoPeg;
	private AutoDriveStraightToPoint drive_offPeg;
	private AutoBasicActuate pnm_OpenPinch;
	private AutoBasicActuate pnm_DropLift;
	
    public GearPlaceAuto() {
        requires(Chassis.GetInstance());
        requires(Robot.bcGearPinch);
        requires(Robot.bcGearLift);
        
        drive_toGear = new DriveToGear();
        drive_ontoPeg = new AutoDriveStraightToPoint();
        drive_offPeg = new AutoDriveStraightToPoint();
        pnm_OpenPinch = new AutoBasicActuate(Robot.bcGearPinch, true);
        pnm_DropLift = new AutoBasicActuate(Robot.bcGearLift, true);
        
        addSequential(drive_toGear,8);
        addSequential(drive_ontoPeg,2);
        addSequential(pnm_OpenPinch,.2);
        addSequential(pnm_DropLift,.2);
        addSequential(drive_offPeg,2);
    }
    
    protected void initialize()
    {
    	drive_toGear.setParam(Preferences.getInstance().getDouble("GearPlace toPeg speed", .5));
    	
    	drive_ontoPeg.SetParam(-8, .5, 0, .25, false);
    	
    	drive_offPeg.SetParam(10, .5, 0, .25, false);
    	
    }
}
