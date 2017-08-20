package org.usfirst.frc.team3130.robot.commands;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;
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
	private Timer timer;
	boolean hasAimed;
	boolean hasTurned;
	boolean isActive;
	private String instance = "";

	public enum AimingMode { kVision, kEncoders }

	
    public TurretAim() {
        requires(TurretAngle.GetInstance());



        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        timer = new Timer();
    }
   
    
    public TurretAim(String instance){
        requires(TurretAngle.GetInstance());

        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        timer = new Timer();
        this.instance = instance;
    }

    public void setMode(AimingMode mode)
    {
    	m_mode = mode;
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

    	hasAimed = false;
    	hasTurned = false;
    	isActive = false;
    	m_mode = AimingMode.kVision;
    	m_angle = TurretAngle.GetInstance().getAngleDegrees();
        timer.start();
        
        m_dist = JetsonInterface.getDouble("Boiler Groundrange", DEFAULTBOILERDISTANCE)
        		*(1/Preferences.getInstance().getDouble("Vision to Inches", RobotMap.RATIO_VISIONTOINCHES));

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double targetAngle;
		double turretAngleValue;
		List<ShooterAimingParameters> aimingReports;
    	if(TurretAngle.GetInstance().isOnTarget() == false){
    		aimingReports = AndroidInterface.GetInstance().getAim(); 
    		if(!aimingReports.isEmpty()){
    			
    			//currently set to grab latest angle value of list
    			targetAngle = (aimingReports.get((aimingReports.size() - 1)).getTurretAngle()).getDegrees();
    			turretAngleValue = TurretAngle.GetInstance().getAngleDegrees();
    			TurretAngle.GetInstance().setAngle(turretAngleValue-targetAngle);
    			
    		}
    	
    	}
    	//check if on target here...
    	seenTarget();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	isActive = false;
    	ShooterWheelsLeft.stop();
    	ShooterWheelsRight.stop();
    	SmartDashboard.putBoolean("Boiler aim", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
