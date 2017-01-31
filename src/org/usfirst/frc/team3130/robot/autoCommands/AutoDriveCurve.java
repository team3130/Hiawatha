package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
/**
 *
 */
public class AutoDriveCurve extends PIDCommand {

	private Timer timer;
	
	private double m_radius;
	private double m_theta;
	private double m_threshold;
	private double m_time;
	private boolean m_shiftLow;
	private boolean m_goRight;
	private double m_speed;
	
    public AutoDriveCurve() {
    	super(0.1, 0, 0);	//TODO: Tune Pid Numbers
		
		timer = new Timer();
		
        requires(Chassis.GetInstance());
    }

    public void SetParam(double radius, double theta, double time, double threshold, boolean shiftLow, boolean goRight){
    	m_radius = radius;
    	m_theta = theta;
    	m_threshold = threshold;
    	m_time = time;
    	m_shiftLow = shiftLow;
    	m_goRight = goRight;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().reset();
    	
    	getPIDController().setAbsoluteTolerance(m_threshold);
    	setPID();
    	
    	Chassis.Shift(m_shiftLow);
    	
    	//define the radii that the robot's middle, right wheels, and left wheels will follow
    	double insideArc = 2.0 * Math.PI * (m_radius - (Chassis.robotWidth / 2)) * (m_theta/360.0);
    	double outsideArc = 2.0 * Math.PI * (m_radius + (Chassis.robotWidth / 2)) * (m_theta/360.0);
    	
    	
    	//set the speed of the inside wheels and set distance for outside wheels
    	if(m_goRight) {
    		Chassis.getFrontR().changeControlMode(TalonControlMode.Speed);
        	Chassis.getFrontR().setFeedbackDevice(FeedbackDevice.QuadEncoder);
        	Chassis.getFrontR().setPID(1, 0, 0); //TODO:Tune PID Numbers
        	Chassis.getFrontR().configEncoderCodesPerRev(1024);
        	Chassis.setSpeedR(insideArc/m_time);
        	//code to set distance of L
    	}
    	else {
    		Chassis.getFrontL().changeControlMode(TalonControlMode.Speed);
    		Chassis.getFrontL().setFeedbackDevice(FeedbackDevice.QuadEncoder);
    		Chassis.getFrontL().setPID(1, 0, 0); //TODO:Tune PID Numbers
    		Chassis.getFrontL().configEncoderCodesPerRev(1024);
    		Chassis.setSpeedL(insideArc/m_time);
    		//code to set distance of R
    	}
		
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
