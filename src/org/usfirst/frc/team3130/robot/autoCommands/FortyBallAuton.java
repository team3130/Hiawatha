package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.commands.DriveHopper;
import org.usfirst.frc.team3130.robot.commands.IntakeUp;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Hopper;
import org.usfirst.frc.team3130.robot.subsystems.IndexMotorLeft;
import org.usfirst.frc.team3130.robot.subsystems.IndexMotorRight;
import org.usfirst.frc.team3130.robot.subsystems.Intake;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FortyBallAuton extends CommandGroup {

	private AutoDriveStraightToPoint 	drive_toHopper;
	private AutoDriveStraightToPoint	drive_backFromHopper;
	private AutoTurn					drive_turnToGoal;
	private SmartShoot					shoot_aimAndShoot;
	private IntakeUp					intake_recoverStartBalls;
	private AutoDelay					delay_waitStartIntake;
	
	public FortyBallAuton() {
		requires(Chassis.GetInstance());
		requires(IndexMotorRight.GetInstance());
		requires(IndexMotorLeft.GetInstance());
		requires(WheelSpeedCalculationsRight.GetInstance());
		requires(WheelSpeedCalculationsLeft.GetInstance());
		requires(ShooterWheelsRight.GetInstance());
		requires(ShooterWheelsLeft.GetInstance());
		requires(Intake.GetInstance());
		requires(Hopper.GetInstance());
		
		drive_toHopper = new AutoDriveStraightToPoint();
		drive_backFromHopper = new AutoDriveStraightToPoint();
		drive_turnToGoal = new AutoTurn();
		shoot_aimAndShoot = new SmartShoot();
		intake_recoverStartBalls = new IntakeUp();
		
		addSequential(drive_toHopper, 4);
		addSequential(drive_backFromHopper, 1);
		addSequential(drive_turnToGoal, 1);
		addParallel(shoot_aimAndShoot);
		addSequential(delay_waitStartIntake, 1.5);
		addSequential(intake_recoverStartBalls, 3);
	}
}
