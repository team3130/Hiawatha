package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DumbGearAuto extends CommandGroup {
	
	private AutoDriveStraightToPoint toPeg;
	private AutoDriveStraightToPoint ontoPeg;
	private AutoDriveStraightToPoint offPeg;
	private AutoTurn turnToPeg;
	private AutoBasicActuate dropGear;
	private AutoBasicActuate closePinch;
	private AutoBasicActuate closePinchStart;
	private AutoBasicActuate dropPinch;
	private AutoBasicActuate upPinch;
	
	public DumbGearAuto() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		
		toPeg = new AutoDriveStraightToPoint();
		ontoPeg = new AutoDriveStraightToPoint();
		offPeg = new AutoDriveStraightToPoint();
		turnToPeg = new AutoTurn();
		dropGear = new AutoBasicActuate(Robot.bcGearPinch, false);
		closePinch = new AutoBasicActuate(Robot.bcGearPinch, true);
		closePinchStart = new AutoBasicActuate(Robot.bcGearPinch, true);
		dropPinch = new AutoBasicActuate(Robot.bcGearLift, true);
		upPinch = new AutoBasicActuate(Robot.bcGearLift, false);
		
		addSequential(closePinchStart, 0.5);
		addSequential(toPeg, 5);
		if(!OI.gearStartPos.getSelected().equals("Center")) addSequential(turnToPeg, 3);
		addSequential(ontoPeg, 5);
		addSequential(dropGear, 0.5);
		addSequential(dropPinch, 0.5);
		addSequential(offPeg, 5);
		addSequential(closePinch, 0.5);
		addSequential(upPinch, 0.5);
	}
	
	@Override
	protected void initialize()
	{
		switch(OI.gearStartPos.getSelected()){
			case "Left":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
				turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Left", -52));
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist Outside", -100), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh Outside", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed Outside", 1),
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist Outside", -41), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh Outside", 1.5), 
						0, 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed Outside", .333) ,
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist Outside", 20), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh Outside", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed Outside", .333),
						false
				);
				
				break;
						
			case "Center":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
				turnToPeg.SetParam(0);
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist", -62), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed", 1),
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist", -17), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed", .333) ,
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist", 15), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed", .333),
						false
				);
				
				break;
				
			case "Right":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 135));
				turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Right", -30));
				
				turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Left", 52));
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist Outside", -100), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh Outside", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed Outside", 1),
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist Outside", -41), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh Outside", 1.5), 
						0, 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed Outside", .333) ,
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist Outside", 20), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh Outside", 1), 
						0, 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed Outside", .333),
						false
				);
				
				break;
		}
				
	}
}
