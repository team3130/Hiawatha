package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SmartShoot extends CommandGroup {

	private CameraAim aim;
	private AutoSmartShoot shoot;
	private BasicSpinMotor feedShooters;
	private AutoDelay wait;
	private BasicSpinMotor hopper2;
	private AutoDelay wait2;
	private JostleIntake spinIntake;
	
	private double indexPercent = .7;
	
    public SmartShoot() {
        requires(ShooterWheelsRight.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btHopper2);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(Robot.btIntake);
        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        requires(Chassis.GetInstance());
        
        aim = new CameraAim("Auton");
        shoot = new AutoSmartShoot();
        feedShooters = new BasicSpinMotor(Robot.btHopper, .5);
        wait = new AutoDelay();
        hopper2 = new BasicSpinMotor(Robot.btHopper2, -.8);
        wait2 = new AutoDelay();
        spinIntake = new JostleIntake();
        
        addParallel(aim);
        addParallel(feedShooters);
        addParallel(shoot);
        addSequential(wait, 1);
        addParallel(hopper2);
        addSequential(wait2,1);
        addParallel(spinIntake);
    }
    
    public void setParam(double indexPercent){
    	this.indexPercent = indexPercent;
    }
    
    @Override
    protected void initialize()
    {
    	shoot.setParam(indexPercent, aim);
    }
}
