package org.usfirst.frc.team3130.robot;

import org.usfirst.frc.team3130.util.ConstantsBase;

public class Constants extends ConstantsBase {
/*refactored for 3130
  @author Eastan
 */
	//Vision tracking centerpoint height on high efficiency boiler

    public static double kCenterOfTargetHeight = 86.0; //Correct 9/11/17, in inches
    
    // Turret mechanical constants
	//TODO:determine constants for turret.
    public static double kHardMaxTurretAngle = 97.0;
    public static double kHardMinTurretAngle = -98.0;
    public static double kSoftMaxTurretAngle = 90.0;
    public static double kSoftMinTurretAngle = -90.0;
    public static double kTurretSafeTolerance = 2.0;
    public static double kTurretOnTargetTolerance = 1.0;
    public static double kTurretRotationsPerTick =  1.0 / 4096.0 * 34.0 / 164.0; //Correct 9/11/17, CTRE Mag encoder connected to gearbox output shaft

    //Turret PID
    // Units: error is 4096 counts/rev. Max output is +/- 1023 units.
    public static double kTurretKp = 0.7;
    public static double kTurretKi = 0.0;
    public static double kTurretKd = 30.0;
    public static double kTurretKf = 0;
    public static int kTurretIZone = (int) (1023.0 / kTurretKp);
    public static double kTurretRampRate = 0;
    public static int kTurretAllowableError = 100;
    
	//Camera in relation to turret.
	//TODO:calibrate camera.
    public static double kCameraXOffset = -6.454;
    public static double kCameraYOffset = 0.0;
    public static double kCameraZOffset = 19.75;
    public static double kCameraPitchAngleDegrees = 35.75; 
    public static double kCameraYawAngleDegrees = -1.0;
    public static double kCameraDeadband = 0.0;
    
    // Goal tracker constants
    public static double kMaxGoalTrackAge = 0.3;
    public static double kMaxTrackerDistance = 18.0;
    public static double kCameraFrameRate = 30.0;
    public static double kTrackReportComparatorStablityWeight = 1.0;
    public static double kTrackReportComparatorAgeWeight = 1.0;
    public static double kTrackReportComparatorSwitchingWeight = 3.0;
    public static double kTrackReportComparatorDistanceWeight = 2.0; // Unused

   
    public static int kAndroidAppTcpPort = 43130;
    
    public static double kLooperDt = 0.1; //TODO:tune looper frequency.

    public String getFileLocation() {
        return "~/constants.txt";
    }
    
	
}