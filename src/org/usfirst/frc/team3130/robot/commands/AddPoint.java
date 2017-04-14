package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculations;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AddPoint extends Command {

	private WheelSpeedCalculations m_wscTarget;
	
    public AddPoint(WheelSpeedCalculations wscTarget) {
    	this.setRunWhenDisabled(true);
    	m_wscTarget = wscTarget;
        requires(m_wscTarget);
    }

    // Called once when the command executes
    protected void initialize() {
    	double dist = JetsonInterface.getDouble("Boiler Groundrange", 120);
    	//double dist = Preferences.getInstance().getDouble("Distance", 120);
    	double speed = Preferences.getInstance().getDouble("Left Speed Setpoint", 0);
    	m_wscTarget.AddPoint(dist, speed);
    }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
