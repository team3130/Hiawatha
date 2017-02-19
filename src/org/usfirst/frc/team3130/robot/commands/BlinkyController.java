package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BlinkyController extends Command {

	//private boolean changed = false;
	
	
    public BlinkyController() {
        // Use requires() here to declare subsystem dependencies
    	requires(Blinky.GetInstance());    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Blinky.runLights("S");
    }

    // Called repeatedly when this Command is scheduled to run
    //Set the pyramid of commands to execute with the top being priority and bottom not mattering
    protected void execute() {
    	if(Chassis.GetShiftedDown()) Blinky.runLights("6");
    	else if( ShooterWheelsLeft.GetError() > 5 ) Blinky.runLights("1");
    	
    	
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
