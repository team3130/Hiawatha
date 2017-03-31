package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;
import org.usfirst.frc.team3130.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SmartShoot extends CommandGroup {

	private CameraAim aim;
	private AutoSmartShoot shoot;
	private BasicSpinMotor feedShooters;
	private BasicSpinMotor runHopper2;
	private AutoDelay delay;
	private RunIndexer indexers;

	
	private double indexPercent = .7;
	
    public SmartShoot() {
        requires(ShooterWheelsRight.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btHopper2);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(WheelSpeedCalculationsLeft.GetInstance());
        requires(WheelSpeedCalculationsRight.GetInstance());
        
        aim = new CameraAim();
        shoot = new AutoSmartShoot();
        indexers = new RunIndexer();
        feedShooters = new BasicSpinMotor(Robot.btHopper, .5);
        runHopper2 = new BasicSpinMotor(Robot.btHopper2, -0.5);
        delay = new AutoDelay(1);
        
        addParallel(aim);
        addParallel(feedShooters);
        addParallel(indexers);
        addParallel(runHopper2);
        addSequential(shoot);
        
        
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
