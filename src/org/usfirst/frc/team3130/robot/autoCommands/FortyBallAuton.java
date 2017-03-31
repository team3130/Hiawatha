package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;

import org.usfirst.frc.team3130.robot.Robot;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;
import org.usfirst.frc.team3130.robot.continuousDrive.*;
import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FortyBallAuton extends CommandGroup {

	private AutoDriveStraightToPoint                  driveForward;
    private AutoTurn               turn_towardsHopper;
    private AutoBasicActuate            dropPinch;
    private AutoDriveStraightToPoint                   drive_toHopper;
	private ShootAfterHopper			auto_shootFromHopper;
	private RunIndexer index;
	
	public FortyBallAuton() {
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(WheelSpeedCalculationsRight.GetInstance());
		requires(WheelSpeedCalculationsLeft.GetInstance());
		requires(ShooterWheelsRight.GetInstance());
		requires(ShooterWheelsLeft.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		
		driveForward = new AutoDriveStraightToPoint();
	    turn_towardsHopper = new AutoTurn();
	    dropPinch = new AutoBasicActuate(Robot.bcGearLift, true);
	    drive_toHopper = new AutoDriveStraightToPoint();
		auto_shootFromHopper = new ShootAfterHopper();
		index = new RunIndexer();

		
		addSequential(driveForward);
		addSequential(turn_towardsHopper);
		addParallel(dropPinch);
		addSequential(drive_toHopper);
		
		addParallel(index);
		addSequential(auto_shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		driveForward.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Forward Dist", -82), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 3), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .7), 
				false
		);
		if (OI.fieldSide.getSelected() == "Red") {
			turn_towardsHopper.SetParam(Preferences.getInstance().getDouble("TurnToHopper Left", -90));
		}
		else {
			turn_towardsHopper.SetParam(Preferences.getInstance().getDouble("TurnToHopper Right", 90));
		}
        drive_toHopper.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Over Dist", -38), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 3), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .7), 
				false
		);
	}
}
