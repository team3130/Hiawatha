package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 */
public class JetsonInterface extends Subsystem {

	//Instance Handling
    private static JetsonInterface m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, JetsonInterface.
     */
    public static JetsonInterface GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new JetsonInterface();
    	return m_pInstance;
    }
    
    private static ITable jetsonTable;
    private static final String TABLENAME = "/Jetson";
    
    private JetsonInterface()
    {
    	jetsonTable = NetworkTable.getTable(TABLENAME);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Generic getter for values from the Jetson
     * 
     * <p>A generic function to get values from the network table accessed by the Jetson. 
     * As it isn't specialized for any of the values, the default needs to be passed in.</p>
     * @param key the key to get the value of
     * @param backup the value to return if the key doesn't exist in the table
     * @return either the value in the table or the backup
     */
    public static double getDouble(String key, double backup)
    {
    	return jetsonTable.getNumber(key, backup);
    }
    
    /**
     * Places a number on the Jetson networktable
     * @param key the key to bind the value to
     * @param data the number to put on the table
     */
    public static void putDouble(String key, double data)
    {
    	jetsonTable.putNumber(key, data);
    }
}

