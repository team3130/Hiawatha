package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
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
	private BasicSpinMotor				intake_recoverStartBalls;
	private AutoDelay					delay_waitStartIntake;
	
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
		drive_backFromHopper = new AutoDriveStraightToPoint();
		drive_turnToGoal = new AutoTurn();
		shoot_aimAndShoot = new SmartShoot();
		intake_recoverStartBalls = new BasicSpinMotor(Robot.btIntake, .6);
		
		addSequential(drive_toHopper, 4);
		addSequential(drive_backFromHopper, 1);
		addSequential(drive_turnToGoal, 1);
		addParallel(shoot_aimAndShoot);
		addSequential(delay_waitStartIntake, 1.5);
		addSequential(intake_recoverStartBalls, 3);
	}
}
