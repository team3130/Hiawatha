package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BasicCylinder extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private Solenoid pnm_actuator;
    
    public BasicCylinder(int PNM_port)
    {
    	pnm_actuator = new Solenoid(PNM_port);
    }
    
    /**
     * Actuates a cylinder
     * <p>Controls the movement of the cylinder. The value passed to the function changes the state of the cylinder, 
     * with true extending the cylinder, and false retracting it.
     * </p>
     * @param extend to extend the cylinder or not, true extends, false retracts
     */
    public void actuate(boolean extend)
    {
    	pnm_actuator.set(extend);
    }
}

