package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class WipeStopPointsL extends InstantCommand {

    public WipeStopPointsL() {
        super();
        requires(WheelSpeedCalculationsLeft.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	WheelSpeedCalculationsLeft.WipeData();
    }

}
