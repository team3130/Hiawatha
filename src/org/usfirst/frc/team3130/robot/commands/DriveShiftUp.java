package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveShiftUp extends Command {

	private Timer timer;
	private boolean m_bShifted;
	
    public DriveShiftUp() {
        // Use requires() here to declare subsystem dependencies
        requires(Chassis.GetInstance());
        timer = new Timer();
        m_bShifted = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	Chassis.TalonsToCoast(true);
    	Chassis.DriveTank(0, 0);		//Cut all power to the motors so they aren't running during the shift
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Execute the shift only once, and only at a certain delay after the motors have been stopped
    	if(!m_bShifted && timer.get() > Preferences.getInstance().getDouble("Shift Dead Time Start", 0.125)){
    		Chassis.Shift(false);
    		m_bShifted = true;
    		//Reset the timer so that the ending dead time is from shifting rather than from the start.
    		timer.reset();
    		timer.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//End the command a configurable length of time after the robot has shifted gears
        return (m_bShifted && timer.get()> Preferences.getInstance().getDouble("Shift Dead Time End", 0.125));
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Set variables to default states for next execution of command
    	m_bShifted = false;
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
