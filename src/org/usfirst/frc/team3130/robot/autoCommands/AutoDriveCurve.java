package org.usfirst.frc.team3130.robot.autoCommands;


import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;


/**
 *
 */
public class AutoDriveCurve extends Command {

	
	private double m_angle;
	private double m_threshold;
	private boolean m_shiftLow;
	private boolean m_turnLeft = false;
	
	private double m_radiusFar;
	
    public AutoDriveCurve() {				
        requires(Chassis.GetInstance());

    }


    /**
     * Sets up the curve to be driven
     * @param radius the radius from to the center of the robot
     * @param threshold the threshold on the distance in inches
     * @param angle the angle to turn in radians
     * @param shiftLow to shift the robot to low gear or not
     */
    public void SetParam(double radius, double threshold, double angle, boolean shiftLow){
    	m_angle = angle;
    	m_threshold = threshold;
    	m_shiftLow = shiftLow;
    	
    	if(radius > 0) m_turnLeft = true;
    	m_radiusFar = Math.abs(radius) + RobotMap.DIM_ROBOTWHEELTOWHEEL/2;
    }
    
    /**
     * Sets up the curve to be driven
     * @param linearDistance the distance in a straight line, as a chord of the curve
     * @param horizontalOffset the distance from the starting point to the tangent line of the ending point of the curve
     * @param shiftLow to shift the robot to low gear or not
     * @param threshold the threshold on the distance in inches
     */
    public void SetParam(double linearDistance, double horizontalOffset, boolean shiftLow, double threshold)
    {
    	m_threshold = threshold;
    	m_shiftLow = shiftLow;
    	
    	m_angle = 2*Math.asin(horizontalOffset/linearDistance);
    	
    	double radius = (linearDistance*linearDistance)/(2*horizontalOffset);
    	if(radius>0) m_turnLeft = true;
    	m_radiusFar = Math.abs(radius) + RobotMap.DIM_ROBOTWHEELTOWHEEL/2;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {  	
    	//Needs to occur for both turn directions
		Chassis.Shift(m_shiftLow);
		Chassis.ReleaseAngle();		
        Chassis.TalonsToCoast(false);

    	if(m_turnLeft){
    		Chassis.setTurnDir(TurnDirection.kLeft);    		
    	}
    	else{
    		Chassis.setTurnDir(TurnDirection.kRight);
    	}
    	
    	Chassis.setPositionTalon(m_angle*m_radiusFar);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Chassis.HoldAngle(Chassis.getPositionTalonSpeed()/m_radiusFar);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Chassis.getPositionTalonError() < m_threshold;
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
