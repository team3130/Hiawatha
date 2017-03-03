package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraAim extends Command {

	private double m_yaw;
	private final double DEFAULTTHRESHOLD = 0.5;
	private final double SHOOTERTHRESHOLD = 5.0;
	private final double DEFAULTBOILERDISTANCE = 120;
	private Timer timer;
	boolean hasAimed;
	
    public CameraAim() {
        requires(Chassis.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(ShooterWheelsRight.GetInstance());
        requires(WheelSpeedCalculationsLeft.GetInstance());
        requires(WheelSpeedCalculationsRight.GetInstance());
        timer = new Timer();

    }

    /**
     * Tells if the Robot is on target and ready to shoot.
     * @return if aimed correctly and wheel up to speed
     */
    public boolean onTarget()
    {
    	return (
    			Math.abs(m_yaw) < Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD)
    		&&	Math.abs(ShooterWheelsLeft.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)
    		&&	Math.abs(ShooterWheelsRight.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD)
    	);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.setTurnDir(TurnDirection.kStraight);
    	ShooterWheelsLeft.setPID();
    	ShooterWheelsRight.setPID();
    	Chassis.SetPIDValues(21);
        Chassis.TalonsToCoast(false);
    	hasAimed = false;
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!hasAimed || timer.get() > Preferences.getInstance().getDouble("Aim Timeout", .5)){
    		if(Math.abs(JetsonInterface.getDouble("Boiler Sys Time", 9999) - JetsonInterface.getDouble("Boiler Time", 0)) < 0.25){
		    	m_yaw = JetsonInterface.getDouble("Boiler Yaw", 0);
		    	Chassis.HoldAngle(m_yaw);
		   	}
    		
    		timer.reset();
    		timer.start();
    		hasAimed = true;
    	}

    	
    	double dist = JetsonInterface.getDouble("Boiler Distance", DEFAULTBOILERDISTANCE);
    	ShooterWheelsLeft.setSpeed(WheelSpeedCalculationsLeft.GetSpeed(dist));
    	ShooterWheelsRight.setSpeed(WheelSpeedCalculationsRight.GetSpeed(dist));
    	
    	Chassis.DriveStraight(-OI.stickL.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	ShooterWheelsLeft.stop();
    	ShooterWheelsRight.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
