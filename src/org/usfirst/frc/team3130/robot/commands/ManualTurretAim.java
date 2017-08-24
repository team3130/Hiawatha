package org.usfirst.frc.team3130.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

public class ManualTurretAim extends Command {

	public ManualTurretAim(){
		requires(TurretAngle.GetInstance());
	}
	
	protected void initialize() {
		//Talon init handled by TurretAngle subsystem
	}
	
	protected void execute() {
    	
    	double turnSpeed = OI.gamepad.getZ(); //getX returns value from -1 to 1
    	
    	//Explicitly turning on Quadratic inputs for drivers, as all other systems will use nonQuadratic
    	TurretAngle.GetInstance().setOpenLoop(turnSpeed);
    }

    // Called once after isFinished returns true
    protected void end() {
    }
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}