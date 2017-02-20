package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.BasicCANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BasicSpinMotor extends Command {

	private double percent;
	private BasicCANTalon motor;
	
	/**
	 * Spins a motor
	 * 
	 * <p>Takes a BasicCANTalon and a percentage, and drives the motor at that percentage. 
	 * It keeps spinning it until another command sets the motor to off</p>
	 * @param motor the BasicCANTalon to work with
	 * @param percentage the percentage of the voltage supplied to the talon to pass onto the motor
	 */
    public BasicSpinMotor(BasicCANTalon motor, double percentage) {
        requires(motor);
        percent = percentage;
        this.motor=motor;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	motor.spinMotor(percent);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
