package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FortyBallAuton extends CommandGroup {

	private AutoDriveStraightToPoint 	drive_toHopper;
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
		
		drive_toHopper = new AutoDriveStraightToPoint();
		auto_shootFromHopper = new ShootAfterHopper();

		
		addSequential(drive_toHopper, 4);
		addSequential(auto_shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		drive_toHopper.SetParam(
				Preferences.getInstance().getDouble("Auto To Hopper Dist", 120), 
				Preferences.getInstance().getDouble("Auto To Hopper Threshold", 1), 
				Preferences.getInstance().getDouble("Auto To Hopper Speed", .7),				//Drive Straight 
				false			//High Gear
		);
	}
}
