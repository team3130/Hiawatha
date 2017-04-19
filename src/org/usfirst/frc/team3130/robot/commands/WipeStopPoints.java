package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculations;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WipeStopPoints extends Command {

	private WheelSpeedCalculations m_wscTarget;
	
    public WipeStopPoints(WheelSpeedCalculations wscTarget) {
    	this.setRunWhenDisabled(true);
    	m_wscTarget = wscTarget;
        requires(m_wscTarget);
    }

    // Called once when the command executes
    protected void initialize() {
    	m_wscTarget.WipeData();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
