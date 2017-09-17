package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.ManualFlywheel;
import org.usfirst.frc.team3130.robot.commands.TurretAim;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SmartShoot extends CommandGroup {

	private TurretAim					shoot_aim;
	private ManualFlywheel				shoot;
	
    public SmartShoot() {
		requires(Robot.wscTurret);
		requires(TurretAngle.GetInstance());
		requires(Robot.btTurretIndex);
		requires(Robot.btTurretHopperL);
		requires(Robot.btTurretHopperR);
        requires(Robot.btIntake);
        requires(Chassis.GetInstance());
        
		shoot_aim = new TurretAim();
		shoot = new ManualFlywheel();
		
		addSequential(shoot_aim, 1.2);
		addSequential(shoot);
    }
    
    
    @Override
    protected void initialize()
    {
    	
    }
    
}
