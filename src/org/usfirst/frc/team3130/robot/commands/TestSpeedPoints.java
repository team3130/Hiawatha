package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestSpeedPoints extends Command {

	Timer timer;
	
	public TestSpeedPoints() {
    	this.setRunWhenDisabled(true);
		
		requires(Robot.wscLeft);
		requires(Robot.wscRight);
		
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double dist = 80 + timer.get()*43;
		SmartDashboard.putNumber("Left SpeedCurve Test", Robot.wscLeft.GetSpeed(dist));
		SmartDashboard.putNumber("Right SpeedCurve Test", Robot.wscRight.GetSpeed(dist));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get()>4;
	}

	// Called once after isFinished returns true
	protected void end() {
		timer.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
