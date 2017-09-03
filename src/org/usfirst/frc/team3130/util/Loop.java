package org.usfirst.frc.team3130.util;

/**
 * Interface for loops, which are routine that run periodically in the robot
 * code (such as periodic gyroscope calibration, etc.)
 * 
 * Loop objects are place on a list used by Looper.java
 * 
 * @see Looper
 */
public interface Loop {
    public void onStart();

    public void onLoop();

    public void onStop();
}
