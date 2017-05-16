package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.*;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDumbShoot extends CommandGroup {

	private double indexPercentage;
	private double leftSpeed;
	private double rightSpeed;
	private BasicSpinMotor hopper;
	private BasicSpinMotor hopper2;
	private BasicSpinMotor rIndex;
	private BasicSpinMotor lIndex;
	private JostleIntake spinIntake;
	private RunWheelsManual shoot;
	private AutoDelay wait;
	
    public AutoDumbShoot() {
        requires(Robot.btLeftIndex);
        requires(Robot.btRightIndex);
        requires(ShooterWheelsRight.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btHopper2);
        requires(Robot.btIntake);
        
        hopper = new BasicSpinMotor(Robot.btHopper, .5);
        hopper2 = new BasicSpinMotor(Robot.btHopper2, -.8);
        rIndex = new BasicSpinMotor(Robot.btLeftIndex, indexPercentage);
        lIndex = new BasicSpinMotor(Robot.btRightIndex, indexPercentage);
        spinIntake = new JostleIntake();
        shoot = new RunWheelsManual();
        wait = new AutoDelay();
        
        addParallel(hopper);
        addParallel(hopper2);
        addParallel(shoot);
        addSequential(wait,1);
        addParallel(rIndex);
        addParallel(lIndex);
        addParallel(spinIntake);
        
    }

    /**
     * The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the index motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public void setParam(double percent, double lSpeed, double rSpeed)
    {
    	indexPercentage = percent;
    	leftSpeed = lSpeed;
    	rightSpeed = rSpeed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	shoot.setParam(leftSpeed, rightSpeed);
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
    	Robot.btLeftIndex.spinMotor(0);
    	Robot.btRightIndex.spinMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
