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
		requires(Robot.bcGearLift);
		
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
		//addSequential(closePinch, 0.5);
		addSequential(upPinch, 0.5);
	}
	
	@Override
	protected void initialize()
	{
		if(OI.fieldSide.getSelected().equals("Red")){
			switch(OI.gearStartPos.getSelected()){
				case "Left":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
					turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Left", -45));
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist RedLeft", -57.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh RedLeft", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed RedLeft", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist RedLeft", -92.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh RedLeft", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed RedLeft", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist RedLeft", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh RedLeft", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed RedLeft", .333),
							false
					);
					
					break;
							
				case "Center":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
					turnToPeg.SetParam(0);
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist", -93+17.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist", -17), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist", 20), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed", .333),
							false
					);
					
					break;
					
				case "Right":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 135));
					turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Right", 45));
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist RedRight", -75+17.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh RedRight", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed RedRight", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist RedRight", -92.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh RedRight", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed RedRight", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist RedRight", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh RedRight", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed RedRight", .333),
							false
					);
					
					break;
			}
		}else{
			switch(OI.gearStartPos.getSelected()){
				case "Left":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
					turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Left", -45));
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist BlueLeft", -74+17.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh BlueLeft", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed BlueLeft", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist BlueLeft", -95.3), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh BlueLeft", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed BlueLeft", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist BlueLeft", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh BlueLeft", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed BlueLeft", .333),
							false
					);
					
					break;
							
				case "Center":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
					turnToPeg.SetParam(0);
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist", -93+17.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist", -17), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist", 20), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed", .333),
							false
					);
					
					break;
					
				case "Right":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 135));
					turnToPeg.SetParam(Preferences.getInstance().getDouble("TurnToGear Right", 45));
					
					toPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear toPeg Dist BlueRight", -74+17.5), 
							Preferences.getInstance().getDouble("DumbGear toPeg Thresh BlueRight", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear toPeg Speed BlueRight", 1),
							false
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist BlueRight", -93.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh BlueRight", 10.0), 
							0, 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed BlueRight", .4) ,
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist BlueRight", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh BlueRight", 1), 
							0, 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed BlueRight", .333),
							false
					);
					
					break;
			}
		}
	}
}
