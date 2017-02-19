package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AddPointL extends InstantCommand {

    public AddPointL() {
        super();
        requires(WheelSpeedCalculationsLeft.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	double dist = JetsonInterface.getDouble("Boiler Distance", 120);
    	double speed = Preferences.getInstance().getDouble("Left Speed Setpoint", 0);
    	WheelSpeedCalculationsLeft.AddPoint(dist, speed);
    }

}
