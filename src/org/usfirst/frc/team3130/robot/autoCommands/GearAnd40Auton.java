package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearAnd40Auton extends CommandGroup {

	GearPlaceAuto placeGear;
	AutoDriveCurve drive_toHopper;
	ShootAfterHopper shoot;
	
    public GearAnd40Auton() {
        requires(Robot.bcGearPinch);
        requires(Robot.bcGearLift);
		requires(Chassis.GetInstance());
		requires(Robot.btLeftIndex);
		requires(Robot.btRightIndex);
		requires(Robot.wscRight);
		requires(Robot.wscLeft);
		requires(ShooterWheelsRight.GetInstance());
		requires(ShooterWheelsLeft.GetInstance());
		requires(Robot.btIntake);
		requires(Robot.btHopper);
		
		placeGear = new GearPlaceAuto();
		drive_toHopper = new AutoDriveCurve();
		shoot = new ShootAfterHopper();
		
		addSequential(placeGear);
		addSequential(drive_toHopper);
		addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {
    	drive_toHopper.SetParam(
    			Preferences.getInstance().getDouble("Gear40 toHopper Downrange", 119), 
    			Preferences.getInstance().getDouble("Gear40 toHopper Crossrange", 27), 
    			false, 
    			1
    	);
    }
}
