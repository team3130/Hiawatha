package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class AutoDriveStraightToPoint extends PIDCommand {
	
	private Timer timer;
	
	private double m_distance;
	private double m_angle;
	private double m_threshold;
	private double m_speed;
	private boolean m_shiftLow;
	
	
	
    public AutoDriveStraightToPoint() {
		super(0.1, 0, 0);	//TODO: Tune Pid Numbers
		
		timer = new Timer();
		
        requires(Chassis.GetInstance());
    }

    public void SetParam(double setpoint, double threshold, double angle, double speed, boolean shiftLow){
    	m_distance = setpoint;
    	m_angle = (Math.PI/180)*angle;
    	m_threshold = threshold;
    	m_speed = speed;
    	m_shiftLow = shiftLow;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().reset();
    	
    	Chassis.Shift(m_shiftLow);
	Chassis.setTurnDir(TurnDirection.kStraight);
    	Chassis.HoldAngle(0);
    	
    	getPIDController().setSetpoint(m_distance + Chassis.GetDistance());
    	getPIDController().setAbsoluteTolerance(m_threshold);
    	setPID();
        Chassis.TalonsToCoast(false);


    	
    	timer.reset();
    	timer.start();
    	
    	getPIDController().enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return getPIDController().onTarget()
        		&& Math.abs(Chassis.GetSpeed()) < 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Chassis.ReleaseAngle();
    	Chassis.DriveTank(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	protected double returnPIDInput() {
		return Chassis.GetDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		if(output > m_speed) output = m_speed;
		else if(output < -m_speed) output = -m_speed;
		
		Chassis.DriveStraight(output);
	}
	
	private void setPID()
	{
		if(!m_shiftLow){
			getPIDController().setPID(
					Preferences.getInstance().getDouble("LowGear Auton Drive P", 0.1), 
					Preferences.getInstance().getDouble("LowGear Auton Drive I", 0), 
					Preferences.getInstance().getDouble("LowGear Auton Drive D", 0)
				); 
		}else{
			getPIDController().setPID(
					Preferences.getInstance().getDouble("HighGear Auton Drive P", 0.1), 
					Preferences.getInstance().getDouble("HighGear Auton Drive I", 0), 
					Preferences.getInstance().getDouble("HighGear Auton Drive D", 0)
				); 
		}
	}
}
