package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurretToAngle extends Command {

	private static double angle;
	
    public TurretToAngle() {
        requires(TurretAngle.GetInstance());
    }

    public void SetParam(double angle){
    	this.angle = angle;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	TurretAngle.setAngle(angle);
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
