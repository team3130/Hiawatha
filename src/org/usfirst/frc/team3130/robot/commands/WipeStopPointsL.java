package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WipeStopPointsL extends Command {

    public WipeStopPointsL() {
    	this.setRunWhenDisabled(true);
        requires(WheelSpeedCalculationsLeft.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	WheelSpeedCalculationsLeft.WipeData();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
