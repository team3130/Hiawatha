package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.TurretFlywheel;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualTurretIntake extends Command {	
	
    public ManualTurretIntake() {
        requires(Robot.btTurretIndex);
        requires(Robot.btTurretHopperL);
        requires(Robot.btTurretHopperR);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.btTurretHopperL.spinMotor(Preferences.getInstance().getDouble("Turret Hopper Motor PercentVBus", 0.3));
		Robot.btTurretHopperR.spinMotor(Preferences.getInstance().getDouble("Turret Hopper Motor PercentVBus", 0.3));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.btTurretIndex.spinMotor(TurretFlywheel.getVBus());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.btTurretIndex.spinMotor(0);
		Robot.btTurretHopperL.spinMotor(0);
		Robot.btTurretHopperR.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
