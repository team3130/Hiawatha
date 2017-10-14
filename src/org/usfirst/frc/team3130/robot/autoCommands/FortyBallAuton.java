package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.ManualTurretIntake;
import org.usfirst.frc.team3130.robot.commands.TurretAim;
import org.usfirst.frc.team3130.robot.commands.TurretToAngle;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class FortyBallAuton extends CommandGroup {

	private AutoDriveStraightToPoint    driveForward;
    private AutoTurn               		turn_towardsHopper;
    private AutoBasicActuate            clampPinch;
    private AutoDriveStraightToPoint    drive_toHopper;
	private ShootAfterHopper			auto_shootFromHopper;
	private TurretToAngle				turnToBoiler;
	private ContDrive					drive_pressToButton;
	
	public FortyBallAuton() {
		requires(Chassis.GetInstance());
		requires(Robot.wscTurret);
		requires(TurretAngle.GetInstance());
		requires(Robot.btTurretIndex);
		requires(Robot.btTurretHopperL);
		requires(Robot.btTurretHopperR);
		
		driveForward = new AutoDriveStraightToPoint();
	    turn_towardsHopper = new AutoTurn();
		turnToBoiler = new TurretToAngle();
	    clampPinch = new AutoBasicActuate(Robot.bcGearPinch, true);
	    drive_toHopper = new AutoDriveStraightToPoint();
		auto_shootFromHopper = new ShootAfterHopper();
		//drive_pressToButton = new ContDrive();

		addParallel(clampPinch, 1);
		addParallel(turnToBoiler, 1);
		addSequential(driveForward,1.6);//3);
		addSequential(turn_towardsHopper,1.7); //2);
		addSequential(drive_toHopper,1.0); //2);
		//addSequential(drive_pressToButton, .5);
		addSequential(auto_shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		TurretAim.SetParam(true);
		
		
		driveForward.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Forward Dist", 100), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .7), 
				false
		);
		if (OI.fieldSide.getSelected() == "Red") {
			turn_towardsHopper.SetParam(-Preferences.getInstance().getDouble("TurnToHopper Right", 90));
		}
		else {
			turn_towardsHopper.SetParam(-Preferences.getInstance().getDouble("TurnToHopper Left", -90));
		}
        drive_toHopper.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Over Dist", 43), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .5), 
				false
		);
        if(OI.fieldSide.getSelected() == "Red") {
    		turnToBoiler.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", -80));
    	}
    	else {
    		turnToBoiler.SetParam(Preferences.getInstance().getDouble("Auto Shoot Turn Angle", 80));
    	}
        
        //drive_pressToButton.SetParam(-.3, -12);
	}
}
