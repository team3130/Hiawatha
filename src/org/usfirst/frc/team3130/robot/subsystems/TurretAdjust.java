package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TurretAdjust extends PIDSubsystem {

	//Instance Handling
    private static TurretAdjust m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
     * 
     * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
     */
    public static TurretAdjust GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new TurretAdjust();
    	return m_pInstance;
    }
    
    private static CANTalon turretAdjust;
    private static boolean turnClockwise;
    
    public void initDefaultCommand() {
        
    }
    
    private TurretAdjust(){
    	super(1.0, 0, 0); //TODO: adjust these
    	turretAdjust = new CANTalon(RobotMap.CAN_TURRETADJUST);
    	turnClockwise = true;
    }
    
    public void turn(boolean clockwise){
    	turnClockwise = clockwise;
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
    
}

