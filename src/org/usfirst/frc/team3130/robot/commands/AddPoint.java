package org.usfirst.frc.team3130.robot.commands;

import java.util.List;

import org.usfirst.frc.team3130.robot.subsystems.AndroidInterface;
import org.usfirst.frc.team3130.robot.subsystems.JetsonInterface;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculations;
import org.usfirst.frc.team3130.robot.vision.ShooterAimingParameters;

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
    	try {
			List<ShooterAimingParameters> aimingReports;
			aimingReports = AndroidInterface.GetInstance().getAim(); 
	    		if(!aimingReports.isEmpty()){
	    	    	double dist = (aimingReports.get((aimingReports.size() - 1)).getRange());
	    	    	//double dist = Preferences.getInstance().getDouble("Distance", 120);
	    	    	double speed = Preferences.getInstance().getDouble("ShooterTest", 0);
	    	    	m_wscTarget.AddPoint(dist, speed);
	    		}
	    
		}catch (NullPointerException e) {
			
		}

    }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
