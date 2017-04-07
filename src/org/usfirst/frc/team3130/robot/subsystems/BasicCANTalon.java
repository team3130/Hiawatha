package org.usfirst.frc.team3130.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class BasicCANTalon extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private CANTalon mc_spinnyMotor;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public BasicCANTalon(int CAN_id, String subsystem, String item)
    {
    	mc_spinnyMotor = new CANTalon(CAN_id);
    	//LiveWindow.addActuator(subsystem, item, mc_spinnyMotor);
    }
    
    /**
     * Spins a motor
     * <p>Controls the speed of the motor, taking a value from -1 to 1 as a percentage of available power to pass on</p>
     * @param percentage the percentage of the voltage being fed to the controlers to pass on to the motors.
     */
    public void spinMotor(double percentage)
    {
    	mc_spinnyMotor.set(percentage);
    }
    
    /**
     * Returns the speed of the motor
     * 
     * <p>Returns the same value that is set by spinMotor, the percentage of voltage getting through the CANTalon</p>
     * @return the percentage of the voltage being fed to the controler that is passed to the motor.
     */
    public double getPercent()
    {
    	return mc_spinnyMotor.get();
    }
    
    public double getCurrent()
    {
    	return mc_spinnyMotor.getOutputCurrent();
    }
}

