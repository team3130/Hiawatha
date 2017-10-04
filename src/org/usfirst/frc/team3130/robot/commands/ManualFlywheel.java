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

	private static double P_DEFAULT = 1.0; //TODO: tune this
    private static double I_DEFAULT = 0.0; //TODO: tune this
    private static double D_DEFAULT = 0.0; //TODO: tune this
    private static double F_DEFAULT = 0.0; //TODO: tune this
    private static int IZONE_DEFAULT = 0; //TODO: tune this
    private static double RAMP_DEFAULT = 0.0; //TODO: tune this
	
    public ManualFlywheel() {
    	System.out.println("ManualFlywheel().....");
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {

    	
    	TurretFlywheel.getMotor().setPID(
    			Preferences.getInstance().getDouble("TurretFlyP",
        		P_DEFAULT),
        		Preferences.getInstance().getDouble("TurretFlyI",
        		I_DEFAULT),
        		Preferences.getInstance().getDouble("TurretFlyD",
        		D_DEFAULT),
        		//Preferences.getInstance().getDouble("TurretFlyF",
        		F_DEFAULT,
        		//Preferences.getInstance().getInt("TurretFlyIZone",
        		IZONE_DEFAULT,
        		//Preferences.getInstance().getDouble("TurretFlyRamp",
        		RAMP_DEFAULT,
        		0);

   
    	//System.out.println("Initialize..........");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	TurretFlywheel.setSpeed(Preferences.getInstance().getDouble("ShooterTest", 3900.0));

    	System.out.println("SHOOTING  MANUAL..........");
    	System.out.println("Speed: " + TurretFlywheel.getSpeed());
    	
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
