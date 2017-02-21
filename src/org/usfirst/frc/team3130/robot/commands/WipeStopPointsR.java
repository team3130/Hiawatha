package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WipeStopPointsR extends Command {

    public WipeStopPointsR() {
    	this.setRunWhenDisabled(true);
        requires(WheelSpeedCalculationsRight.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	WheelSpeedCalculationsRight.WipeData();
    }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
