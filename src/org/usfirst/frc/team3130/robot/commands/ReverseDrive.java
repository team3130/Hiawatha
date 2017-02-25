package org.usfirst.frc.team3130.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import java.lang.Math;
import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.*;

/**
 * Turns the robot 180 degrees and makes the front the back
 */
public class ReverseDrive extends Command {

	private static double error;
	
    public ReverseDrive() {
        requires(Chassis.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.TalonsToCoast(true);
    	Chassis.DriveTank(0,0);
    	Chassis.HoldAngle(180);
    	Chassis.ReverseDrive();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs(Chassis.GetPIDError()) <= 90)
    		Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Chassis.GetPIDError()) <= error;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
