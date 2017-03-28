package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;
import org.usfirst.frc.team3130.robot.continuousDrive.*;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FortyBallAuton extends CommandGroup {

	private ContDrive                   driveForward;
    private ContTurnDist                turn_towardsHopper;
    private ContDrive                   drive_toHopper;
	private ShootAfterHopper			auto_shootFromHopper;
	
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
		
		driveForward = new ContDrive();
	    turn_towardsHopper = new ContTurnDist();
	    drive_toHopper = new ContDrive();
		auto_shootFromHopper = new ShootAfterHopper();

		addSequential(driveForward);
		addSequential(turn_towardsHopper);
		addSequential(drive_toHopper);
		
		addSequential(auto_shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		driveForward.SetParam(.6, 30);//TODO: find this value (distance out to hopper)
		if (OI.fieldSide.getSelected() == "Red") {
			turn_towardsHopper.SetParam(.8, -90*Math.PI/180f); //TODO: check if direction is correct
		}
		else {
			turn_towardsHopper.SetParam(.8, 90*Math.PI/180f);
		}
        drive_toHopper.SetParam(.6, 30);//TODO: find this value (distance over to hopper)
	}
}
