package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.BasicSpinMotor;
import org.usfirst.frc.team3130.robot.commands.AutoFlywheel;
import org.usfirst.frc.team3130.robot.commands.ManualTurretIntake;
import org.usfirst.frc.team3130.robot.commands.TurretToAngle;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NoVisionShootAfterHopper extends CommandGroup {

	private TurretToAngle        turnTurret;             
	private BasicSpinMotor       intakeUp;
	
	private int                  wheelSpeed;
	private ManualTurretIntake   turretIntake;
	private JostleIntake         spinIntake;
	private AutoFlywheel       shoot;
	
	private AutoDelay            delay1;
	
    public NoVisionShootAfterHopper() {
		requires(Chassis.GetInstance());
		requires(TurretAngle.GetInstance());
		requires(Robot.btTurretIndex);
        requires(Robot.btTurretHopperL);
        requires(Robot.btTurretHopperR);
        
        wheelSpeed = 3500; //TODO: determine speed for distance
		
		turnTurret =    new TurretToAngle();
		shoot =         new AutoFlywheel();
		delay1 =        new AutoDelay();
		intakeUp =      new BasicSpinMotor(Robot.btIntake, .6);
		
        turretIntake =  new ManualTurretIntake();
        spinIntake =    new JostleIntake();
        shoot =         new AutoFlywheel();

		addSequential(delay1, 1.5);
		addParallel(intakeUp);
        addParallel(shoot);
        addParallel(turretIntake);
        addParallel(spinIntake);
    }
    
    @Override
    protected void initialize()
    {
    	shoot.SetParam(Preferences.getInstance().getInt("Auto Hopper Shoot Speed", wheelSpeed));
    }
}
