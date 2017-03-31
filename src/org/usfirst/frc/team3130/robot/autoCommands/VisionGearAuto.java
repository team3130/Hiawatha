package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;
import org.usfirst.frc.team3130.robot.continuousDrive.ContTurnDist;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionGearAuto extends CommandGroup {
	
	private DriveToGear uptoPeg;
	private DriveToGear finalAim;
	private AutoDelay wait;
	private ContDrive toPeg;
	private AutoDriveStraightToPoint ontoPeg;
	private AutoDriveStraightToPoint offPeg;
	private ContTurnDist turnToPeg;
	private AutoBasicActuate dropGear;
	private AutoBasicActuate closePinchStart;
	private AutoBasicActuate dropPinch;
	private AutoBasicActuate upPinch;
	
	public VisionGearAuto() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
		
		uptoPeg = new DriveToGear();
		finalAim = new DriveToGear();
		wait = new AutoDelay(.5);
		toPeg = new ContDrive();
		ontoPeg = new AutoDriveStraightToPoint();
		offPeg = new AutoDriveStraightToPoint();
		turnToPeg = new ContTurnDist();
		dropGear = new AutoBasicActuate(Robot.bcGearPinch, false);
		closePinchStart = new AutoBasicActuate(Robot.bcGearPinch, true);
		dropPinch = new AutoBasicActuate(Robot.bcGearLift, true);
		upPinch = new AutoBasicActuate(Robot.bcGearLift, false);
		
		addSequential(closePinchStart, 0.5);
		addSequential(toPeg, 3);
		if(!OI.gearStartPos.getSelected().equals("Center")) addSequential(turnToPeg, 3);
		addSequential(uptoPeg, 5);
		addSequential(wait, .5);
		addSequential(finalAim, .5);
		addSequential(ontoPeg, 2);
		addSequential(dropGear, 0.5);
		addSequential(dropPinch, 0.5);
		addSequential(offPeg, 3);
		addSequential(upPinch, 0.5);
	}
	
	@Override
	protected void initialize()
	{
		uptoPeg.setParam(.2);
		
		if(OI.fieldSide.getSelected().equals("Red")){
			switch(OI.gearStartPos.getSelected()){
				case "Left":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
					turnToPeg.SetParam(1, Preferences.getInstance().getDouble("TurnToGear Left", -45)*(Math.PI/180f));
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed RedLeft", .7),
							Preferences.getInstance().getDouble("DumbGear toPeg Dist RedLeft", -79+17.5)
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist RedLeft", -92.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh RedLeft", 40.0), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed RedLeft", .4), 
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist RedLeft", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh RedLeft", 3), 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed RedLeft", .333), 
							false
					);
					
					break;
							
				case "Center":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
					turnToPeg.SetParam(1, 0);
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed", .7),
							Preferences.getInstance().getDouble("DumbGear toPeg Dist", -93+17.5+12)
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
					turnToPeg.SetParam(1, Preferences.getInstance().getDouble("TurnToGear Right", 45)*(Math.PI/180f));
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed RedRight", .7),
							Preferences.getInstance().getDouble("DumbGear toPeg Dist RedRight", -79+17.5)
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist RedRight", -92.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh RedRight", 40.0), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed RedRight", .4), 
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist RedRight", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh RedRight", 3), 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed RedRight", .333), 
							false
					);
					
					break;
			}
		}else{
			switch(OI.gearStartPos.getSelected()){
				case "Left":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Close Angle", -10));
					turnToPeg.SetParam(1, Preferences.getInstance().getDouble("TurnToGear Left", -45)*(Math.PI/180f));
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed BlueLeft", .7), 
							Preferences.getInstance().getDouble("DumbGear toPeg Dist BlueLeft", -79+17.5)
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist BlueLeft", -95.3), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh BlueLeft", 40.0), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed BlueLeft", .4), 
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist BlueLeft", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh BlueLeft", 3), 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed BlueLeft", .333), 
							false
					);
					
					break;
							
				case "Center":
					//turnToPeg.SetParam(Preferences.getInstance().getDouble("AimFromGear Center Angle", 60));
					turnToPeg.SetParam(1, 0);
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed", .7), 
							Preferences.getInstance().getDouble("DumbGear toPeg Dist", -93+17.5)
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist", -17), 
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
					turnToPeg.SetParam(1, Preferences.getInstance().getDouble("TurnToGear Right", 45)*(Math.PI/180f));
					
					toPeg.SetParam(
							-Preferences.getInstance().getDouble("DumbGear toPeg Speed BlueRight", .7), 
							Preferences.getInstance().getDouble("DumbGear toPeg Dist BlueRight", -79+17.5)
					);
					
					ontoPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear ontoPeg Dist BlueRight", -93.8), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Thresh BlueRight", 40.0), 
							Preferences.getInstance().getDouble("DumbGear ontoPeg Speed BlueRight", .4), 
							false
					);
					
					offPeg.SetParam(
							Preferences.getInstance().getDouble("DumbGear offPeg Dist BlueRight", 25), 
							Preferences.getInstance().getDouble("DumbGear offPeg Thresh BlueRight", 3), 
							Preferences.getInstance().getDouble("DumbGear offPeg Speed BlueRight", .333), 
							false
					);
					
					break;
			}
		}
	}
}
