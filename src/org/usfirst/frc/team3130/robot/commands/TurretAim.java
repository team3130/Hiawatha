package org.usfirst.frc.team3130.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;
import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculations;
import org.usfirst.frc.team3130.robot.subsystems.AndroidInterface;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.vision.ShooterAimingParameters;



import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aims the TurretAngle
 */

//TODO:This command still needs reworking @author Eastan

public class TurretAim extends Command {

	protected ShooterAimingParameters shooter_aiming_parameters;
	private String instance = "";
	private final double DEFAULTTHRESHOLD = 2;
	private final double SHOOTERTHRESHOLD = 100;

	public enum AimingMode { kVision, kEncoders }

    public TurretAim() {
        requires(TurretAngle.GetInstance());
		requires(TurretFlywheel.GetInstance());
		requires(Robot.wscTurret);
		requires(Robot.btTurretIndex);
        requires(Robot.btTurretHopperL);
        requires(Robot.btTurretHopperR);
    }
   
    
    public TurretAim(String instance){
        requires(TurretAngle.GetInstance());
        this.instance = instance;
    }


    /**
     * Tells if the Robot has seen a target recently
     */
    public boolean seenTarget()
    {

    	if(AndroidInterface.targetTracking() == true ){
    		
    			SmartDashboard.putBoolean("Boiler Seen" + instance, true);
    			return true;
    	}
    	else{
    			SmartDashboard.putBoolean("Boiler Seen" + instance, false);
    		
    	}
    	
    	return false;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	//TurretAngle already handles its own PID value settings
    	//TODO:Calibrate turret to absolute position

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double targetAngle;
		double turretAngleValue;
		boolean onTarget;
		double distanceToBoiler;
		double targetSpeed;
		try {
			List<ShooterAimingParameters> aimingReports;
	    	{
	    		aimingReports = AndroidInterface.GetInstance().getAim(); 
	    		if(!aimingReports.isEmpty()){
	    			
	    			//currently set to grab latest angle value of list
	    			targetAngle = (aimingReports.get((aimingReports.size() - 1)).getTurretAngle()).getDegrees();
	    			turretAngleValue = TurretAngle.getAngleDegrees();
	    			System.out.println("target angle  " + targetAngle );
	    			System.out.println("current angle " + turretAngleValue );
	    			System.out.println("set to angle  " + (turretAngleValue + targetAngle));
	    			TurretAngle.setAngle(turretAngleValue+targetAngle);
	    			
	    			//Access current distance and use wheel speed calculations
	    			distanceToBoiler = (aimingReports.get((aimingReports.size() - 1)).getRange());
	    			targetSpeed = Robot.wscTurret.GetSpeed(distanceToBoiler);
	    			TurretFlywheel.setSpeed(targetSpeed);
	    			
	    			onTarget = ((Math.abs(targetAngle - turretAngleValue) < (Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD)))
	    	        		 && (Math.abs(TurretFlywheel.getSpeed() - targetSpeed) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)));
	    			
	    			if(onTarget){
	    				Robot.btTurretHopperL.spinMotor(Preferences.getInstance().getDouble("Turret Hopper Motor PercentVBus", -0.3));
	    				Robot.btTurretHopperR.spinMotor(Preferences.getInstance().getDouble("Turret Hopper Motor PercentVBus", 0.3));
	    				Robot.btTurretIndex.spinMotor(TurretFlywheel.getVBus() * -1);
	    			}
	    		}
	    	
	    	}
		}catch (NullPointerException e) {
			
		}finally {
			//check if target is found here...
	    	//(updates smartdashboard)
	    	seenTarget();
		}
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putBoolean("Boiler aim", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
