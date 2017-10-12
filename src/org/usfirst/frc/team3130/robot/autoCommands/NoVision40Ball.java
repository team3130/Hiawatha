package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.continuousDrive.ContDrive;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NoVision40Ball extends CommandGroup {

	private AutoDriveStraightToPoint    driveForward;
    private AutoTurn               		turn_towardsHopper;
    private AutoBasicActuate            clampPinch;
    private AutoDriveStraightToPoint    drive_toHopper;
	private NoVisionShootAfterHopper	shootFromHopper;
	private ContDrive					drive_pressToButton;
	
	public NoVision40Ball() {
		requires(Chassis.GetInstance());
		requires(TurretAngle.GetInstance());
		
		driveForward = new AutoDriveStraightToPoint();
	    turn_towardsHopper = new AutoTurn();
	    clampPinch = new AutoBasicActuate(Robot.bcGearPinch, true);
	    drive_toHopper = new AutoDriveStraightToPoint();
		shootFromHopper = new NoVisionShootAfterHopper();
		drive_pressToButton = new ContDrive();

		addParallel(clampPinch, 1);
		addSequential(driveForward,2);//3);
		addSequential(turn_towardsHopper,1.7);//2);
		addSequential(drive_toHopper,1.5);//2);
		addSequential(drive_pressToButton, .5);
		addSequential(shootFromHopper);
	}
	
	@Override
	protected void initialize()
	{
		driveForward.SetParam(
				-Preferences.getInstance().getDouble("Forty Ball Forward Dist", 43), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .7), 
				false
		);
		if (OI.fieldSide.getSelected() == "Red") {
			turn_towardsHopper.SetParam(Preferences.getInstance().getDouble("TurnToHopper Left", 90));
		}
		else {
			turn_towardsHopper.SetParam(Preferences.getInstance().getDouble("TurnToHopper Right", -90));
		}
        drive_toHopper.SetParam(
				Preferences.getInstance().getDouble("Forty Ball Over Dist", -50), 
				Preferences.getInstance().getDouble("Forty Ball Thresh", 20), 
				Preferences.getInstance().getDouble("Forty Ball Speed", .5), 
				false
		);
        
        drive_pressToButton.SetParam(-.3, -12);
	}
}
