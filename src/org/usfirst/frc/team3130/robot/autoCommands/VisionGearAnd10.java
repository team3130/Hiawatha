package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Climber;
import org.usfirst.frc.team3130.robot.subsystems.TurretAngle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionGearAnd10 extends CommandGroup {

	private VisionGearAuto placeGear;
	private AutoDriveStraightToPoint backMore;
	private AutoTurn faceTarget;
	private SmartShoot shoot;
	private CameraDrive closeToTarget;
	
    public VisionGearAnd10() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        requires(TurretAngle.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btHopper2);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        requires(Climber.GetInstance());
        
        placeGear = new VisionGearAuto();
        backMore = new AutoDriveStraightToPoint();
        faceTarget = new AutoTurn();
        shoot = new SmartShoot();
        closeToTarget = new CameraDrive();
        
        addSequential(placeGear);
        if((OI.fieldSide.getSelected().equals("Red") && OI.gearStartPos.getSelected().equals("Left"))
        		|| (OI.fieldSide.getSelected().equals("Blue") && OI.gearStartPos.getSelected().equals("Right"))){
        	addSequential(backMore, 2);
        }
        addSequential(faceTarget, 2);
        addSequential(closeToTarget, 3);
        addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {

    	backMore.SetParam(
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Distance", 12), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Thresh", 2), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Speed", .5), 
    			false
    	);
    	
    	switch (OI.fieldSide.getSelected()) {
			case "Red":
				
				switch(OI.gearStartPos.getSelected()){
					case "Right":
				    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Close", 15));
						break;
							
					case "Center":
				    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Center", 70.6));
						break;
						
					case "Left":
						faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Far", 15));
						break;
				}
				
				break;
	
			case "Blue":
				switch(OI.gearStartPos.getSelected()){
				case "Left":
			    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Close", 15));
					break;
						
				case "Center":
			    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Center", -70.6));
					break;
					
					case "Right":
					faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Far", 15));
						break;
				}
			break;
		}
    }
}
