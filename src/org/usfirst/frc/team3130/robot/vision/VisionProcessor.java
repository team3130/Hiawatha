package org.usfirst.frc.team3130.robot.vision;

import org.usfirst.frc.team3130.robot.subsystems.AndroidInterface;
import org.usfirst.frc.team3130.robot.vision.VisionUpdate;
import org.usfirst.frc.team3130.robot.vision.VisionUpdateReceiver;
import org.usfirst.frc.team3130.util.Loop;;

/**
 * This function adds vision updates (from the Nexus smartphone) to a list in
 * RobotState. This helps keep track of goals detected by the vision system. The
 * code to determine the best goal to shoot at and prune old Goal tracks is in
 * GoalTracker.java
 * 
 * @see GoalTracker.java
 */
public class VisionProcessor implements Loop, VisionUpdateReceiver {
    static VisionProcessor instance_ = new VisionProcessor();
    VisionUpdate update_ = null;
    AndroidInterface robot_state_ = AndroidInterface.GetInstance();

    public static VisionProcessor getInstance() {
        return instance_;
    }

    VisionProcessor() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onLoop() {
        VisionUpdate update;
        synchronized (this) {
            if (update_ == null) {
                return;
            }
            update = update_;
            update_ = null;
        }
        robot_state_.addVisionUpdate(update.getCapturedAtTimestamp(), update.getTargets());
    }

    @Override
    public void onStop() {
        // no-op
    }

    @Override
    public synchronized void gotUpdate(VisionUpdate update) {
        update_ = update;
    }

}
