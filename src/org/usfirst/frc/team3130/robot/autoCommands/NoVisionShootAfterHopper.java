package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NoVisionShootAfterHopper extends CommandGroup {

	private AutoDriveStraightToPoint	drive_backFromHopper;
	private AutoTurn					drive_turnToGoal;
	private AutoDumbShoot				shoot;
	private AutoDelay					delay_Generic1;
	private BasicSpinMotor				intake_Up;
	
    public NoVisionShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(ShooterWheelsRight.GetInstance());
		requires(ShooterWheelsLeft.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		requires(Robot.btHopper2);
		
		drive_backFromHopper = new AutoDriveStraightToPoint();
		drive_turnToGoal = new AutoTurn();
		shoot = new AutoDumbShoot();
		delay_Generic1 = new AutoDelay();
		intake_Up = new BasicSpinMotor(Robot.btIntake, .6);

		addSequential(delay_Generic1, 1.5);
		addParallel(intake_Up);
		addSequential(drive_backFromHopper, 1);
		addSequential(drive_turnToGoal, 1);
		addParallel(shoot);
    }
    
    @Override
    protected void initialize()
    {
    	
    	drive_backFromHopper.SetParam(
    			Preferences.getInstance().getDouble("Drive Back Hopper Dist", 24), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Threshold", 1), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Speed", .66), 				//Drive Straight
    			Chassis.GetShiftedDown()	//Stay in current gear
    	);
    	
    	
    	if(OI.fieldSide.getSelected() == "Red") {
    		drive_turnToGoal.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", 90));
    	}
    	else {
    	drive_turnToGoal.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", -80));
    	}
    	
    	shoot.setParam(
    			Preferences.getInstance().getDouble("Auto Shoot Index Percent", .7),
    			Preferences.getInstance().getDouble("Auto Shoot Right Speed", 1500),
    			Preferences.getInstance().getDouble("Auto Shoot Left Speed", 1500)
    			);
    }
}
