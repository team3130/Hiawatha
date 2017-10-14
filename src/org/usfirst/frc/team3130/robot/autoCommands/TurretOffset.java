package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.commands.HoldAngle;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurretOffset extends Command {

	private double offsetAngle = 2.0;

	
    public TurretOffset() {
        requires(TurretAngle.GetInstance());
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	if(OI.fieldSide.getSelected() == "Blue"){
			HoldAngle.setAngle(TurretAngle.getAngleDegrees() - offsetAngle);
		}else {
			HoldAngle.setAngle(TurretAngle.getAngleDegrees() + offsetAngle);
		}

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}