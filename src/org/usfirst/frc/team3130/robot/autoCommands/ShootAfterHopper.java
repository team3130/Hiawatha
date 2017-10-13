package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.commands.AutoFlywheel;
import org.usfirst.frc.team3130.robot.commands.ManualTurretIntake;
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

	private TurretAim					shoot_aim;
	private TurretToAngle				turnToBoiler;
	private AutoFlywheel				shoot;
	private ManualTurretIntake			elevator;
	
    public ShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(TurretAngle.GetInstance());
		requires(TurretFlywheel.GetInstance());
		

		turnToBoiler = new TurretToAngle();
		shoot_aim = new TurretAim();
		shoot = new AutoFlywheel();
		elevator = new ManualTurretIntake();
		
		addSequential(turnToBoiler, 0.7);
		addSequential(shoot_aim, 1.5);
		addSequential(shoot);
		addParallel(elevator);
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
    	
    	/*drive_backFromHopper.SetParam(
    			-Preferences.getInstance().getDouble("Drive Back Hopper Dist", -24), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Threshold", 1), 
    			Preferences.getInstance().getDouble("Drive Back Hopper Speed", -.66), 				//Drive Straight
    			Chassis.GetShiftedDown()	//Stay in current gear
    	);*/
    	
    }
}
