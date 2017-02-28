package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldAngleTest extends Command {

	private double m_angle;
	
    public HoldAngleTest() {
        requires(Chassis.GetInstance());
    }

    public void SetParam(double angle)
    {
    	m_angle = angle;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.HoldAngle(m_angle);
    	Chassis.GetInstance().setAbsoluteTolerance(0.5);
    	Chassis.DriveStraight(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Chassis.DriveStraight(Chassis.getReverseMultiplier() * -1 * OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    	//return Chassis.GetInstance().onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Chassis.ReleaseAngle();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
