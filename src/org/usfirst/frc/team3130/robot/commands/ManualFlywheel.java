package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualFlywheel extends Command {

	private static int wheelSpeedTarget; 
	private static int WHEELSPEEDDEFAULT = 3500; //TODO: determine speed for sweet spot
	
    public ManualFlywheel() {
        requires(TurretFlywheel.GetInstance());
    }

    public void SetParam(int speed){
    	wheelSpeedTarget = speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	wheelSpeedTarget = Preferences.getInstance().getInt("Turret Wheel Speed", WHEELSPEEDDEFAULT);
    	TurretFlywheel.setSpeed(wheelSpeedTarget);
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
    	TurretFlywheel.setOpenLoop(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
