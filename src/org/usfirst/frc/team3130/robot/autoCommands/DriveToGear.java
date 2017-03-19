package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {

	private double speed;
	private boolean setSpeed;
	
	private Timer timer_Timeout;
	private Timer timer_cameraLag;
	private boolean hasAimed;
	private boolean hasTurned;
	private boolean onTarget;
	private double prevYaw;
	private double prevTime;
	
	public DriveToGear() {
		requires(Chassis.GetInstance());
		setSpeed = false;
		timer_Timeout = new Timer();
		//timer_cameraLag = new Timer();
	}

	public void setParam(double speed)
	{
		this.speed = -speed;
		setSpeed = true;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Chassis.setTurnDir(TurnDirection.kStraight);
		ShooterWheelsLeft.setPID();
		ShooterWheelsRight.setPID();
		Chassis.SetPIDValues(21);
		Chassis.TalonsToCoast(false);
		hasAimed = false;
		hasTurned = false;
		onTarget = false;
		prevYaw = 0;
		prevTime = 0;
		timer_Timeout.start();
		Chassis.HoldAngle(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//Implemented from https://i.imgur.com/B9THPiA.png
		//double cr = 0;
		// When the peg orientation can be tracked more stable use this cross range
		//double cr = -JetsonInterface.getDouble("Peg Crossrange", 0);
		//double dr = JetsonInterface.getDouble("Peg Downrange", 1);
		//double yaw = -JetsonInterface.getDouble("Peg Yaw", 0);
		
		//double alpha = Math.atan2(cr, dr);
		//double beta = Math.atan2(2*cr, dr);
		
		//double angle = alpha - beta - yaw;	//Extends theta's endpoint to be coincident to alpha's, then goes back alpha degrees
		
		if(hasAimed){
			if(hasTurned){
				double yaw = JetsonInterface.getDouble("Peg Yaw", 0);
				if(Math.abs(yaw - prevYaw) < Preferences.getInstance().getDouble("Yaw Threshold", 1)){	//Check for safe current data before turing off of it
					hasTurned = false;
					hasAimed = false;
					onTarget = true;
				}
				else {
					double time = JetsonInterface.getDouble("Peg Time", 9999);
					if (time > prevTime) {
						prevYaw = yaw;
						prevTime = time;
					}
				}
			}
			
			else 
			if(Math.abs(Chassis.GetRate()) <= Preferences.getInstance().getDouble("Turning stopped", .1)){		//Check for finished moving before aiming againg
				hasTurned = true;
				//timer_cameraLag.reset();
				//timer_cameraLag.start();
			}
		}
		else
		if(Math.abs(JetsonInterface.getDouble("Peg Sys Time", 0) - JetsonInterface.getDouble("Peg Time", 9999)) < Preferences.getInstance().getDouble("Gear Time", 0.25)) {
			double yaw = JetsonInterface.getDouble("Peg Yaw", 0);
			Chassis.HoldAngle(yaw);
			hasAimed = true;
			if(Math.abs(yaw) > Preferences.getInstance().getDouble("Peg Camera Thresh", 1)) {
				onTarget = false;
			}
		}
		else {
			// Not aimed, not turned, no vision available -- nothing to do.
		}

		if(setSpeed) {
			Chassis.DriveStraight(speed);
		}
		else {
			Chassis.DriveStraight(-OI.stickL.getY());
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(setSpeed) return onTarget;
		else return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		timer_Timeout.stop();
		timer_Timeout.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
