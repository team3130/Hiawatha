package org.usfirst.frc.team3130.robot.commands;

import java.util.List;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.AndroidInterface;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;
import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;
import org.usfirst.frc.team3130.robot.vision.ShooterAimingParameters;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualFlywheel extends Command {

	private static double wheelSpeedTarget; 
	private static double WHEELSPEEDDEFAULT = 800.0; //TODO: determine speed for sweet spot
	
    public ManualFlywheel() {
    }

    public void SetParam(int speed){
    	wheelSpeedTarget = speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	wheelSpeedTarget = WHEELSPEEDDEFAULT;
    	TurretFlywheel.setSpeed(wheelSpeedTarget);
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	double distanceToBoiler;
    	double targetSpeed;
    
    	try {
			List<ShooterAimingParameters> aimingReports;
			aimingReports = AndroidInterface.GetInstance().getAim(); 
	    		if(!aimingReports.isEmpty()){
	    			distanceToBoiler = (aimingReports.get((aimingReports.size() - 1)).getRange());
	    			//targetSpeed = Robot.wscTurret.GetSpeed(distanceToBoiler);
	    			TurretFlywheel.setSpeed(Preferences.getInstance().getDouble("ShooterTest", 800.0));
	    			System.out.println("SHOOTING..........");
	    		}
	    
		}catch (NullPointerException e) {
		}
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
