package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class WipeStopPointsR extends InstantCommand {

    public WipeStopPointsR() {
        super();
        requires(WheelSpeedCalculationsRight.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	WheelSpeedCalculationsRight.WipeData();
    }

}
