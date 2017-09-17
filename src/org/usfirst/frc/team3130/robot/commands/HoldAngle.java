package org.usfirst.frc.team3130.robot.commands;


import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldAngle extends Command {
	static double holdAngle;

	
    public HoldAngle() {
    	holdAngle = 0.0;
    }
    
    public static void setAngle(double angle) {
        holdAngle = angle; 
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	TurretAngle.setAngle(holdAngle);
    	System.out.println("HOLDING ANGLE.........");
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
    	end();
    }
}