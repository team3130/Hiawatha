package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
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
public class ShootAfterHopper extends CommandGroup {

	private AutoDriveStraightToPoint	drive_backFromHopper;
	private AutoTurn					drive_turnToGoal;
	private SmartShoot					shoot_aimAndShoot;
	private BasicSpinMotor				intake_recoverStartBalls;
	private AutoDelay					delay_Generic;
	
    public ShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(WheelSpeedCalculationsRight.GetInstance());
		requires(WheelSpeedCalculationsLeft.GetInstance());
		requires(ShooterWheelsRight.GetInstance());
		requires(ShooterWheelsLeft.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		requires(Robot.btHopper2);
		
		drive_backFromHopper = new AutoDriveStraightToPoint();
		drive_turnToGoal = new AutoTurn();
		shoot_aimAndShoot = new SmartShoot();
		intake_recoverStartBalls = new BasicSpinMotor(Robot.btIntake, .6);

		addSequential(delay_Generic, 1);
		addSequential(drive_backFromHopper, 1);
		addSequential(drive_turnToGoal, 1);
		addParallel(shoot_aimAndShoot);
		addSequential(delay_Generic, 1.5);
		addSequential(intake_recoverStartBalls, 3);
    }
    
    @Override
    protected void initialize()
    {
    	drive_backFromHopper.SetParam(
    			Preferences.getInstance().getDouble("Drive Back Hopper Dist", -12), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Threshold", 1), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Speed", .66), 				//Drive Straight
    			Chassis.GetShiftedDown()	//Stay in current gear
    	);
    	
    	drive_turnToGoal.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", 90));
    	
    	shoot_aimAndShoot.setParam(Preferences.getInstance().getDouble("Auto Shoot Index Percent", .2));
    }
}
