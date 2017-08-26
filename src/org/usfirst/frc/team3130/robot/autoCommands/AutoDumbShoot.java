package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.autoCommands.RunWheelsManual;
import org.usfirst.frc.team3130.robot.commands.*;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDumbShoot extends CommandGroup {

	private double leftSpeed;
	private double rightSpeed;
	private BasicSpinMotor hopper;
	private BasicSpinMotor hopper2;
	private DumbIndexerRun indexers;
	private JostleIntake spinIntake;
	private RunWheelsManual shoot;
	private AutoDelay wait;
	
    public AutoDumbShoot() {
        requires(Robot.btLeftIndex);
        requires(Robot.btRightIndex);
        requires(TurretAngle.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btHopper2);
        requires(Robot.btIntake);
        
        hopper = new BasicSpinMotor(Robot.btHopper, .5);
        hopper2 = new BasicSpinMotor(Robot.btHopper2, -.8);
        indexers = new DumbIndexerRun();
        spinIntake = new JostleIntake();
        shoot = new RunWheelsManual();
        wait = new AutoDelay();
        
        addParallel(hopper);
        addParallel(hopper2);
        addParallel(shoot);
        addSequential(wait,1);
        addParallel(indexers);
        addParallel(spinIntake);
        
    }

    /**
     * The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the index motor.
     */
    public void setParam(double lSpeed, double rSpeed)
    {
    	leftSpeed = lSpeed;
    	rightSpeed = rSpeed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	shoot.setParam(leftSpeed, rightSpeed);
    	indexers.setParam(.8);
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
