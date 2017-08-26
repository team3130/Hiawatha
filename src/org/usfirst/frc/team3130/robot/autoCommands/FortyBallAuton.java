package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class FortyBallAuton extends CommandGroup {

	private AutoBasicActuate			hopperDown;
	private AutoDriveStraightToPoint    driveForward;
    private AutoTurn               		turn_towardsHopper;
    private AutoBasicActuate            clampPinch;
    private AutoDriveStraightToPoint    drive_toHopper;
	private ShootAfterHopper			auto_shootFromHopper;
	private ContDrive					drive_pressToButton;
	
	public FortyBallAuton() {
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(Robot.wscRight);
		requires(Robot.wscLeft);
		requires(TurretAngle.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		requires(Robot.bcHopperFloor);
		
		hopperDown = new AutoBasicActuate(Robot.bcHopperFloor, true);
		driveForward = new AutoDriveStraightToPoint();
	    turn_towardsHopper = new AutoTurn();
	    clampPinch = new AutoBasicActuate(Robot.bcGearPinch, true);
	    drive_toHopper = new AutoDriveStraightToPoint();
		auto_shootFromHopper = new ShootAfterHopper();
		drive_pressToButton = new ContDrive();

		addParallel(hopperDown, 1);
		addParallel(clampPinch, 1);
		addSequential(driveForward,3);
		addSequential(turn_towardsHopper,2);
		addSequential(drive_toHopper,2);
		addSequential(drive_pressToButton, .5);
		addSequential(auto_shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		driveForward.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Forward Dist", -119), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
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
				Preferences.getInstance().getDouble("Forty Ball Over Dist", -60), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .5), 
				false
		);
        
        drive_pressToButton.SetParam(-.3, -12);
	}
}
