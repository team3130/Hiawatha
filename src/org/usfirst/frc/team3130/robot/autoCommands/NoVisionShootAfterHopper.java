package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NoVisionShootAfterHopper extends CommandGroup {

	private AutoDriveStraightToPoint	drive_backFromHopper;
	private AutoDriveStraightToPoint	drive_upToGoal;
	private AutoTurn					drive_turnToGoal;
	private AutoDelay					delay_Generic1;
	private BasicSpinMotor				intake_Up;
	
	private double leftSpeed;
	private double rightSpeed;
	private BasicSpinMotor hopper;
	private BasicSpinMotor hopper2;
	private DumbIndexerRun indexers;
	private JostleIntake spinIntake;
	private RunWheelsManual shoot;
	
    public NoVisionShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(TurretAngle.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		requires(Robot.btHopper2);
		
		drive_backFromHopper = new AutoDriveStraightToPoint();
		drive_upToGoal = new AutoDriveStraightToPoint();
		drive_turnToGoal = new AutoTurn();
		shoot = new RunWheelsManual();
		delay_Generic1 = new AutoDelay();
		intake_Up = new BasicSpinMotor(Robot.btIntake, .6);
		
        hopper = new BasicSpinMotor(Robot.btHopper, .5);
        hopper2 = new BasicSpinMotor(Robot.btHopper2, -.8);
        indexers = new DumbIndexerRun();
        spinIntake = new JostleIntake();
        shoot = new RunWheelsManual();

		addSequential(delay_Generic1, 1.5);
		addParallel(intake_Up);
        addParallel(shoot);
		addSequential(drive_backFromHopper, 1);
		addSequential(drive_turnToGoal, 1);
		//addSequential(drive_upToGoal, 2);
        addParallel(hopper);
        addParallel(hopper2);
        addParallel(indexers);
        addParallel(spinIntake);
    }
    
    @Override
    protected void initialize()
    {
    	shoot.setParam(leftSpeed, rightSpeed);
    	indexers.setParam(.8);
    	
    	drive_backFromHopper.SetParam(
    			Preferences.getInstance().getDouble("Drive Back Hopper Dist", 18), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Threshold", 1), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Speed", .66), 				//Drive Straight
    			Chassis.GetShiftedDown()	//Stay in current gear
    	);
    	
    	
    	drive_upToGoal.SetParam(
    			Preferences.getInstance().getDouble("Drive to Goal Dist", 20), 
    			Preferences.getInstance().getDouble("Drive to Goal Thresh", 25), 
    			Preferences.getInstance().getDouble("Drive to Goal Speed", .8), 				//Drive Straight
    			false	//Stay in current gear
    	);
    	
    	if(OI.fieldSide.getSelected() == "Red") {
    		drive_turnToGoal.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", 95.5));
    	}
    	else {
    	drive_turnToGoal.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", -95.5));
    	}
    	
    	shoot.setParam(
    			Preferences.getInstance().getDouble("Auto Shoot Left Speed", 3700),
    			Preferences.getInstance().getDouble("Auto Shoot Right Speed", 3650)
    			);
    }
}
