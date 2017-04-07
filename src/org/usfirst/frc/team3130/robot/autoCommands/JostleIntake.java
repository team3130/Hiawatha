package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JostleIntake extends Command {

	private Timer timer;
	private boolean up;
	
    public JostleIntake() {
    	requires(Robot.btIntake);
    	
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	up = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(up){
    		Robot.btIntake.spinMotor(.6);
    		if(timer.get()>1){
    			timer.stop();
    			timer.reset();
    			timer.start();
    			up=false;
    		}
    	}else{
    		Robot.btIntake.spinMotor(-.6);
    		if(timer.get()>.25){
    			timer.stop();
    			timer.reset();
    			timer.start();
    			up=true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.btIntake.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
