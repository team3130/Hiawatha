package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearBeaterBar extends Subsystem {

	//Instance Handling
    private static GearBeaterBar m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, GearBeaterBar.
     */
    public static GearBeaterBar GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new GearBeaterBar();
    	return m_pInstance;
    }
	
	private static CANTalon mc_GearBar;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    private GearBeaterBar()
    {
    	mc_GearBar = new CANTalon(RobotMap.CAN_GEARBAR);
    }
    
    /**
     * Drives the hopper stirrer
     * <p> This function will drive the gear beater bar. The function takes a value from -1.0 to 1.0 which is the percentage of the 
     * voltage provided to the talon which should be passed on to the bar motor.
     * @param percent the percentage of the voltage available to the talon to drive at
     */
    public static void driveBar(double percent)
    {
    	mc_GearBar.set(percent);
    }
}

