package org.usfirst.frc.team3130.robot.commands;


import org.usfirst.frc.team3130.robot.subsystems.Flashlight;

import edu.wpi.first.wpilibj.command.Command;

public class FlashlightToggle extends Command {
	
    public FlashlightToggle() {
        // Use requires() here to declare subsystem dependencies
        requires(Flashlight.GetInstance());
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Flashlight.Activate(!Flashlight.getActivate());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}