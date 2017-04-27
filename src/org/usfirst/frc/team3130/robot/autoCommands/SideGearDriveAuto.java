package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.continuousDrive.ContTurnDist;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideGearDriveAuto extends CommandGroup {

	private DumbGearAuto placeGear;
	private ContTurnDist turnToField;
	private ContDrive driveDownField;
	
    public SideGearDriveAuto() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        
        placeGear = new DumbGearAuto();
        turnToField = new ContTurnDist();
        driveDownField = new ContDrive();
        
        addSequential(placeGear);
        addSequential(turnToField, 2);
        addSequential(driveDownField, 10);
    }
    
    @Override
    protected void initialize()
    {
    	if(OI.fieldSide.getSelected() == "Blue"){
    		turnToField.SetParam(.5, -120*(Math.PI/180f));
    	}
    	else{
    		turnToField.SetParam(.5, 120*(Math.PI/180f));
    	}
    	driveDownField.SetParam(.7, Preferences.getInstance().getDouble("Side Gear Drive Dist", 220));
    }
}
