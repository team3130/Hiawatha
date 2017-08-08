package org.usfirst.frc.team3130.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3130.robot.vision.ShooterAimingParameters;
import org.usfirst.frc.team3130.robot.vision.GoalTracker.TrackReport;
import org.usfirst.frc.team3130.robot.vision.GoalTracker;
import org.usfirst.frc.team3130.robot.Constants;
import org.usfirst.frc.team3130.util.Rotation2d;
import org.usfirst.frc.team3130.util.Translation2d;


import org.usfirst.frc.team3130.robot.vision.TargetInfo;

import org.usfirst.frc.team3130.util.RigidTransform2d;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AndroidInterface extends Subsystem {

    protected GoalTracker goal_tracker_;
    protected Rotation2d camera_pitch_correction_;
    protected Rotation2d camera_yaw_correction_;

    protected double differential_height_;
    
    //Instance Handling
  	private static AndroidInterface m_pInstance;
  	/**
  	 * A system for getting an instance of this class.
  	 * The function provides a method by which the class is setup as a singleton
  	 * with only a single copy of it existing in memory.
  	 * <p> It will return a reference to the class, which is shared amongst all callers of GetInstance()
  	 * 
  	 * @return the reference to the class referred to in GetInstance. In this case, ShooterWheelsRight.
  	 */
  	public static AndroidInterface GetInstance()
  	{
  		if(m_pInstance == null) m_pInstance = new AndroidInterface();
  		return m_pInstance;
  	}
  	
    public synchronized void reset(double start_time, RigidTransform2d initial_field_to_vehicle,
            Rotation2d initial_turret_rotation) {

        goal_tracker_ = new GoalTracker();
        camera_pitch_correction_ = Rotation2d.fromDegrees(-Constants.kCameraPitchAngleDegrees);
        camera_yaw_correction_ = Rotation2d.fromDegrees(-Constants.kCameraYawAngleDegrees);
        differential_height_ = Constants.kCenterOfTargetHeight - Constants.kCameraZOffset;
    }
    


    //addVisionUpdate called by VisionProcessor.java
    public void addVisionUpdate(double timestamp, List<TargetInfo> vision_update) {
    		List<Translation2d> field_to_goals = new ArrayList<>();
    		if (!(vision_update == null || vision_update.isEmpty())) {
    			for (TargetInfo target : vision_update) {
                    //target.z will be stored in "x" variable of Translation2d, to get z value, use Translation2d.getX()
                    field_to_goals.add( new Translation2d(target.getZ() , target.getY()));
             
            	}
    		}
        synchronized (this) {
            goal_tracker_.update(timestamp, field_to_goals);
        }
    }
    public synchronized void resetVision() {
        goal_tracker_.reset();
    }

    public List<ShooterAimingParameters> getAim(){
    	List<TrackReport> visionReports = goal_tracker_.getTracks();
    	List<ShooterAimingParameters> aimingReports = new ArrayList<>();
        for (TrackReport vreport : visionReports) {
        	double targetY = vreport.field_to_goal.getY();
        	double targetZ = vreport.field_to_goal.getX();
        	double ydeadband = (targetY > -Constants.kCameraDeadband
                    && targetY < Constants.kCameraDeadband) ? 0.0 : targetY;

            // Compensate for camera yaw
            double xyaw = 1.0 * camera_yaw_correction_.cos() + ydeadband * camera_yaw_correction_.sin();
            double yyaw = ydeadband * camera_yaw_correction_.cos() - 1.0 * camera_yaw_correction_.sin();
            double zyaw = targetZ;

            // Compensate for camera pitch
            double xr = zyaw * camera_pitch_correction_.sin() + xyaw * camera_pitch_correction_.cos();
            double yr = yyaw;
            double zr = zyaw * camera_pitch_correction_.cos() - xyaw * camera_pitch_correction_.sin();

            // find intersection with the goal
            if (zr > 0) {
                double scaling = differential_height_ / zr;
                double distance = Math.hypot(xr, yr) * scaling;
                Rotation2d angle = new Rotation2d(xr, yr, true);
                //range to object, and angle
                aimingReports.add(new ShooterAimingParameters(distance, angle , 1));
               
            }
            
        }
        	
        return aimingReports;

    }
    public static boolean targetTracking() {
    	return m_pInstance.goal_tracker_.hasTracks();
    }

@Override
protected void initDefaultCommand() {
	// TODO Auto-generated method stub
	
}
}