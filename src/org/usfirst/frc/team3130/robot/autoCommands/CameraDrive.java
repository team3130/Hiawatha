package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class CameraDrive extends PIDCommand {

	private double m_distance;
	private double m_speed;
	private final double DEFAULTTHRESHOLD = 2;
	private final double DEFAULTSWEETSPOT = 107.6;
	private Timer timer;
	boolean hasAimed;
	
    public CameraDrive() {
    	super(0.1, 0, 0);
        requires(Chassis.GetInstance());
        timer = new Timer();
        m_distance = 0;
        m_speed = 0.75;
    }

	private void setPID()
	{
			getPIDController().setPID(
					Preferences.getInstance().getDouble("LowGear Auton Drive P", 0.069), 
					Preferences.getInstance().getDouble("LowGear Auton Drive I", 3.325e-05), 
					Preferences.getInstance().getDouble("LowGear Auton Drive D", 0.325)
				); 
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().reset();
    	getPIDController().disable();
    	
    	Chassis.Shift(false);
    	Chassis.setTurnDir(TurnDirection.kStraight);
    	Chassis.HoldAngle(0);
    	
    	getPIDController().setAbsoluteTolerance(Preferences.getInstance().getDouble("Boiler Drive Threshold", DEFAULTTHRESHOLD));
    	setPID();
        Chassis.TalonsToCoast(false);
    	
    	timer.reset();
    	timer.start();
    	hasAimed = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!hasAimed){
    		if(Math.abs(JetsonInterface.getDouble("Boiler Sys Time", 9999) - JetsonInterface.getDouble("Boiler Time", 0)) < 0.25){
		    	m_distance = JetsonInterface.getDouble("Boiler Distance", 0);
		    	if(Math.abs(Chassis.GetSpeed()) < Preferences.getInstance().getDouble("Drive Stop Speed", 1)
		    			&& 48 < m_distance && m_distance < 240) {
		        	getPIDController().setSetpoint(
		        			Chassis.GetDistance() +
		        			m_distance -
		        			Preferences.getInstance().getDouble("Camera Drive Sweet", DEFAULTSWEETSPOT)
		        			);
		    		getPIDController().enable();
		    		hasAimed = true;
		    	}
		   	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return getPIDController().onTarget()
        		&& Math.abs(Chassis.GetSpeed()) < Preferences.getInstance().getDouble("Drive Stop Speed", 1);
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
}
