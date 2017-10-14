package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.AutoFlywheel;
import org.usfirst.frc.team3130.robot.commands.ManualTurretIntake;
import org.usfirst.frc.team3130.robot.commands.TurretAim;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SmartShoot extends CommandGroup {

	private TurretAim					shoot_aim;
	private AutoFlywheel				shoot;
	private ManualTurretIntake 			elevator;
	private AutoDelay					delay;
	
    public SmartShoot() {
		requires(Robot.wscTurret);
		requires(TurretAngle.GetInstance());
		requires(Robot.btTurretIndex);
		requires(Robot.btTurretHopperL);
		requires(Robot.btTurretHopperR);
        requires(Robot.btIntake);
        requires(Chassis.GetInstance());
        
		shoot_aim = new TurretAim();
		shoot = new AutoFlywheel();
		elevator = new ManualTurretIntake();
		delay = new AutoDelay();
		
		addSequential(shoot_aim, 0.7);
		addParallel(shoot);
		addSequential(delay, 1.2);
		addParallel(elevator);
    }
    
    
    @Override
    protected void initialize()
    {
    	
    }
    
}
