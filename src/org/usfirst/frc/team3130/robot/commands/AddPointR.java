package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AddPointR extends InstantCommand {

    public AddPointR() {
        super();
        requires(WheelSpeedCalculationsRight.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	//double dist = JetsonInterface.getDouble("Boiler Distance", 120);
    	double dist = Preferences.getInstance().getDouble("Distance", 120);
    	double speed = Preferences.getInstance().getDouble("Right Speed Setpoint", 0);
    	WheelSpeedCalculationsRight.AddPoint(dist, speed);
    }

}
