package org.usfirst.frc.team3130.robot.autoCommands;

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
		dropGear = new AutoBasicActuate(Robot.bcGearPinch, false);
		closePinch = new AutoBasicActuate(Robot.bcGearPinch, true);
		closePinchStart = new AutoBasicActuate(Robot.bcGearPinch, true);
		dropPinch = new AutoBasicActuate(Robot.bcGearLift, true);
		upPinch = new AutoBasicActuate(Robot.bcGearLift, false);
		
		addSequential(closePinchStart, 0.5);
		//addSequential(new AutoDelay(), 1);
		addSequential(toPeg, 5);
		//addSequential(new AutoDelay(), 1);
		addSequential(ontoPeg, 5);
		//addSequential(new AutoDelay(), 1);
		addSequential(dropGear, 0.5);
		addSequential(dropPinch, 0.5);
		//addSequential(new AutoDelay(), 1);
		addSequential(offPeg, 5);
		//addSequential(new AutoDelay(), 1);
		addSequential(closePinch, 0.5);
		addSequential(upPinch, 0.5);
	}
	
	@Override
	protected void initialize()
	{
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
	}
}
