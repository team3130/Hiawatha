package org.usfirst.frc.team3130.robot;

import org.usfirst.frc.team3130.util.ConstantsBase;

public class Constants extends ConstantsBase {
/*refactored for 3130
  @author Eastan
 */
	
    // Turret mechanical constants
	//TODO:determine constants for turret.
    public static double kHardMaxTurretAngle = 109.5;
    public static double kHardMinTurretAngle = -116.5;
    public static double kSoftMaxTurretAngle = 108.0;
    public static double kSoftMinTurretAngle = -115.0;
    public static double kTurretSafeTolerance = 2.0;
    public static double kTurretOnTargetTolerance = 1.0;
    public static double kTurretRotationsPerTick = 14.0 / 50.0 * 14.0 / 322.0;

    
	//camera in relation to turret.
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