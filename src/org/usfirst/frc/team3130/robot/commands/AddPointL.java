package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AddPointL extends Command {

    public AddPointL() {
    	this.setRunWhenDisabled(true);
        requires(WheelSpeedCalculationsLeft.GetInstance());
    }

    // Called once when the command executes
    protected void initialize() {
    	double dist = JetsonInterface.getDouble("Boiler Groundrange", 120);
    	//double dist = Preferences.getInstance().getDouble("Distance", 120);
    	double speed = Preferences.getInstance().getDouble("Left Speed Setpoint", 0);
    	WheelSpeedCalculationsLeft.AddPoint(dist, speed);
    }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
