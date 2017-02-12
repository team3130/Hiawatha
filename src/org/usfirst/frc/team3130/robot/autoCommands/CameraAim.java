package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheels;
import org.usfirst.frc.team3130.robot.subsystems.Chassis.TurnDirection;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraAim extends Command {

	private double m_yaw;
	private final double DEFAULTTHRESHOLD = 0.5;
	private final double SHOOTERTHRESHOLD = 5.0;
	
    public CameraAim() {
        requires(Chassis.GetInstance());
        requires(ShooterWheels.GetInstance());
    }

    /**
     * Tells if the Robot is on target and ready to shoot.
     * @return if aimed correctly and wheel up to speed
     */
    public boolean onTarget()
    {
    	return (m_yaw < Preferences.getInstance().getDouble("Boiler Threshold", DEFAULTTHRESHOLD))
    			&& Math.abs(ShooterWheels.GetError()) < Preferences.getInstance().getDouble("ShooterWheel Tolerance", SHOOTERTHRESHOLD);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Chassis.setTurnDir(TurnDirection.kStraight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_yaw = JetsonInterface.getDouble("Boiler Yaw", 0);
    	Chassis.HoldAngle(m_yaw);
    	Chassis.DriveStraight(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	ShooterWheels.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}