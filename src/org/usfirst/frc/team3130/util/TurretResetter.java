package org.usfirst.frc.team3130.util;

import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

/**
 * Periodically checks if the pivoting turret hits either extreme (remember, the
 * turret cannot spin in a complete circle). There are limit switches at both
 * extremes of the turret, and this checks if the limit switches are activated.
 * If so, resets actual hard angle in TurretAngle's Talon.
 * 
 * TurretResetter calibrates the turret using limit switch positions as reference points.
 */
public class TurretResetter implements Loop {

    TurretAngle mTurretAngle = TurretAngle.GetInstance();

    @Override
    public void onStart() {
        // no-op
    }

    @Override
    public void onLoop() {
        if (mTurretAngle.getForwardLimitSwitch()) {
        	mTurretAngle.resetTurretAtMax();
        } else if (mTurretAngle.getReverseLimitSwitch()) {
            mTurretAngle.resetTurretAtMin();
        }
    }

    
    @Override
    public void onStop() {
        // no-op
    }

}
