package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BlinkyController extends Command {

	//private boolean changed = false;
	private String lastComm = "5";
	private Boolean failed = true;
	
    public BlinkyController() {
        // Use requires() here to declare subsystem dependencies
    	requires(Blinky.GetInstance());    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try{
    	Blinky.runLights("5");
    	failed = false;
    	}finally{}
    }

    // Called repeatedly when this Command is scheduled to run
    //Set the pyramid of commands to execute with the top being priority and bottom not mattering
    protected void execute() {
    	if(!Blinky.random()){
    	if(lastComm != "2" && Chassis.GetShiftedDown()) {
    		Blinky.runLights("2");
    		lastComm = "2";
    	}
    	else if(lastComm != "3" && !Chassis.GetShiftedDown()) {
    		Blinky.runLights("3");
    		lastComm = "3";
    	}
    	} else if(lastComm != "5" && Blinky.random()){
    		Blinky.runLights("5");
    		lastComm = "5";
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false || failed;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
