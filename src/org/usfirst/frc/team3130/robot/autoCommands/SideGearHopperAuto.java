package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.continuousDrive.ContTurnDist;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideGearHopperAuto extends CommandGroup {

	private DumbGearAuto placeGear;
	private ContTurnDist turnToField;
	private ContTurnDist turnToHopper;
	private ContTurnDist turnBackToField;
	private ContDrive driveUptoHopper;
	private ContDrive driveHitHopper;
	private ContDrive driveOffHopper;
	private ContDrive driveDownField;
	
    public SideGearHopperAuto() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        
        placeGear = new DumbGearAuto();
        turnToField = new ContTurnDist();
        driveUptoHopper = new ContDrive(turnToField);
        turnToHopper = new ContTurnDist();
        driveHitHopper = new ContDrive(turnToHopper);
        driveOffHopper = new ContDrive(driveHitHopper);
        turnBackToField = new ContTurnDist();
        driveDownField = new ContDrive(turnBackToField);
        
        
        addSequential(placeGear);
        addSequential(turnToField, 2);
        addSequential(driveUptoHopper);
        addSequential(turnToHopper);
        addSequential(driveHitHopper,2);
        addSequential(driveOffHopper,2);
        addSequential(turnBackToField);
        addSequential(driveDownField);
    }
    
    @Override
    protected void initialize()
    {
    	turnToField.SetParam(.5, -120*(Math.PI/180f));
    	driveUptoHopper.SetParam(.7, 58);
    	turnToHopper.SetParam(.5, 90*(Math.PI/180f));
    	driveHitHopper.SetParam(.7, 57);
    	driveOffHopper.SetParam(-.7, -59);
    	turnBackToField.SetParam(.5, -90*(Math.PI/180f));
    	driveDownField.SetParam(.85, Preferences.getInstance().getDouble("Side Gear Drive Dist", 200)-60);
    }
}
