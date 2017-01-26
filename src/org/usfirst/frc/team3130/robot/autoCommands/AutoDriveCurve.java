package org.usfirst.frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
/**
 *
 */
public class AutoDriveCurve extends PIDCommand {

	private Timer timer;
	
	private double m_arcLength;
	private double m_angle;
	private double m_threshold;
	private double m_speed;
	private boolean m_shiftLow;
	private boolean m_right;
	private double m_totalTime;
	private double m_arcLR;
	private double m_arcLL;
	private double m_speedL;
	private double m_speedR;
	
    public AutoDriveCurve() {
    	super(0.1, 0, 0);	//TODO: Tune Pid Numbers
		
		timer = new Timer();
		
        requires(Chassis.GetInstance());
    }

    public void SetParam(double arcLength, double threshold, double angle, double speed, boolean shiftLow, boolean goRight){
    	m_arcLength = arcLength;
    	m_angle = angle;
    	m_threshold = threshold;
    	m_speed = speed;
    	m_shiftLow = shiftLow;
    	m_right = goRight;
    	m_totalTime = m_arcLength / m_speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().reset();
    	
    	getPIDController().setAbsoluteTolerance(m_threshold);
    	setPID();
    	
    	Chassis.Shift(m_shiftLow);
    	
    	double R = (m_arcLength*360)/(2*Math.PI*m_angle);
    	double insideR = R - (Chassis.InchesWheelToWheel / 2);
    	double outsideR = R + (Chassis.InchesWheelToWheel / 2);
    	
    	if (m_right) {
    		m_arcLL = 2.0 * Math.PI * outsideR * (m_angle/360.0);
    		m_arcLR = 2.0 * Math.PI * insideR * (m_angle/360.0);
    	}
    	else {
    		m_arcLL = 2.0 * Math.PI * insideR * (m_angle/360.0);
    		m_arcLR = 2.0 * Math.PI * outsideR * (m_angle/360.0);
    	}
    	
		m_speedL = m_arcLL / m_totalTime;
		m_speedR = m_arcLR / m_totalTime;
		
    	getPIDController().enable();
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return getPIDController().onTarget();
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
		else if(output < m_speed) output = -m_speed;
		
		Chassis.DriveStraight(output);
	}
    
    private void setPID()
	{
		if(m_shiftLow){
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
