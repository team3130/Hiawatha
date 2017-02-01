package org.usfirst.frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import com.ctre.CANTalon.TalonControlMode;
/**
 *
 */
public class AutoDriveCurve extends Command {

	
	private double m_angle;
	private double m_threshold;
	private boolean m_shiftLow;
	private boolean m_turnLeft = false;
	
	private double m_radiusFar;
	private double m_radiusNear;
	private double m_conversionRatio;	//Converts from the far side to the near side
	
    public AutoDriveCurve() {				
        requires(Chassis.GetInstance());
    }

    /**
     * 
     * @param radius
     * @param threshold
     * @param angle
     * @param shiftLow
     */
    public void SetParam(double radius, double threshold, double angle, boolean shiftLow){
    	m_angle = angle;
    	m_threshold = threshold;
    	m_shiftLow = shiftLow;
    	
    	if(radius > 0) m_turnLeft = true;
    	m_radiusNear = radius - RobotMap.DIM_ROBOTWHEELTOWHEEL/2;
    	m_radiusFar = radius + RobotMap.DIM_ROBOTWHEELTOWHEEL/2;
    	m_conversionRatio = m_radiusNear/m_radiusFar;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {  	
    	//Needs to occur for both turn directions
		Chassis.Shift(m_shiftLow);
		Chassis.setTalonPID();
		Chassis.ReleaseAngle();
    	
    	if(m_turnLeft){
    		Chassis.setRightMotorMode(TalonControlMode.Position);
    		Chassis.setLeftMotorMode(TalonControlMode.Speed);
    		

    		Chassis.setRightTalon((m_angle*Math.PI*m_radiusFar)/180);
    	}else{
    		Chassis.setLeftMotorMode(TalonControlMode.Position);
    		Chassis.setRightMotorMode(TalonControlMode.Speed);
    		
    		Chassis.setLeftTalon((m_angle*Math.PI*m_radiusFar)/180);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(m_turnLeft){
    		Chassis.setLeftTalon(m_conversionRatio * Chassis.GetSpeedR());
    	}else{
    		Chassis.setRightTalon(m_conversionRatio * Chassis.GetSpeedL());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(m_turnLeft) return Chassis.getRightTalonError() < m_threshold;
    	return Chassis.getLeftTalonError() < m_threshold;
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
}
