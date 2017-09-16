package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.commands.TurretAim;
import org.usfirst.frc.team3130.robot.commands.TurretToAngle;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;
import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootAfterHopper extends CommandGroup {

	private AutoDriveStraightToPoint	drive_backFromHopper;
	private TurretAim					shoot_aimAndShoot;
	private AutoDelay					delay_Generic1;
	private AutoDelay					delay_Generic2;
	private AutoDelay					delay_waitData;
	private TurretToAngle				turnToBoiler;
	
    public ShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(TurretAngle.GetInstance());
		requires(TurretFlywheel.GetInstance());
		
		drive_backFromHopper = new AutoDriveStraightToPoint();
		delay_waitData = new AutoDelay();
		turnToBoiler = new TurretToAngle();
		delay_Generic1 = new AutoDelay();
		delay_Generic2 = new AutoDelay();
		shoot_aimAndShoot = new TurretAim();

		addSequential(delay_Generic1, 1.5);
		addSequential(drive_backFromHopper, 1);
		addSequential(delay_waitData, 1);
		addSequential(turnToBoiler);
		addSequential(delay_Generic2, 1);
		addParallel(shoot_aimAndShoot);
    }
    
    @Override
    protected void initialize()
    {
    	if(OI.fieldSide.getSelected() == "Red") {
    		turnToBoiler.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", -90));
    	}
    	else {
    		turnToBoiler.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", 90));
    	}
    	
    	drive_backFromHopper.SetParam(
    			Preferences.getInstance().getDouble("Drive Back Hopper Dist", -24), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Threshold", 1), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Speed", -.66), 				//Drive Straight
    			Chassis.GetShiftedDown()	//Stay in current gear
    	);
    	
    }
}
