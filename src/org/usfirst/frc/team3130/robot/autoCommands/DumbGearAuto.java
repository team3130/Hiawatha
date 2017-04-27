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
	private AutoBasicActuate closePinchStart;
	private AutoBasicActuate dropPinch;
	private AutoBasicActuate upPinch;
	
	public DumbGearAuto() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
		
		toPeg = new AutoDriveStraightToPoint();
		ontoPeg = new AutoDriveStraightToPoint();
		offPeg = new AutoDriveStraightToPoint();
		turnToPeg = new AutoTurn();
		dropGear = new AutoBasicActuate(Robot.bcGearPinch, false);
		closePinchStart = new AutoBasicActuate(Robot.bcGearPinch, true);
		dropPinch = new AutoBasicActuate(Robot.bcGearLift, true);
		upPinch = new AutoBasicActuate(Robot.bcGearLift, false);
		
		addSequential(closePinchStart, 0.5);
		addSequential(toPeg, 3);
		if(!OI.gearStartPos.getSelected().equals("Center")) addSequential(turnToPeg, 3);
		addSequential(ontoPeg, 3);
		addSequential(dropGear, 0.5);
		addSequential(dropPinch, 0.5);
		addSequential(offPeg, 3);
		addSequential(upPinch, 0.5);
	}
	
	@Override
	protected void initialize()
	{
		switch(OI.gearStartPos.getSelected()){
			case "Left":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
				turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Left", -45));
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist Left", -69), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh Left", 3), 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed Left", .6), 
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist Left", -92.8), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh Left", 40.0), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed Left", .5), 
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist Left", 25), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh Left", 3), 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed Left", .333), 
						false
				);
				
				break;
						
			case "Center":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
				turnToPeg.SetParam(0);
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist", -93+17.5+12), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh", 3), 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed", .7), 
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist", -12), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh", 10.0), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed", .4), 
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist", 20), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh", 3), 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed", .333), 
						false
				);
				
				break;
				
			case "Right":
				//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 135));
				turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Right", 45));
				
				toPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear toPeg Dist Right", -71), 
						Preferences.getInstance().getDouble("DumbGear toPeg Thresh Right", 3), 
						Preferences.getInstance().getDouble("DumbGear toPeg Speed Right", .6), 
						false
				);
				
				ontoPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear ontoPeg Dist Right", -92.8), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh Right", 40.0), 
						Preferences.getInstance().getDouble("DumbGear ontoPeg Speed Right", .5), 
						false
				);
				
				offPeg.SetParam(
						Preferences.getInstance().getDouble("DumbGear offPeg Dist Right", 25), 
						Preferences.getInstance().getDouble("DumbGear offPeg Thresh Right", 3), 
						Preferences.getInstance().getDouble("DumbGear offPeg Speed Right", .333), 
						false
				);
				
				break;
		}
	}
}
