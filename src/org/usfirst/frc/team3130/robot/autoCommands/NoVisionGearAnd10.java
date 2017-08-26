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
public class NoVisionGearAnd10 extends CommandGroup {

	private DumbGearAuto placeGear;
	private AutoDriveStraightToPoint backMore;
	private AutoTurn faceTarget;
	private AutoDumbShoot shoot;
	private AutoDriveStraightToPoint closeToTarget;
	
    public NoVisionGearAnd10() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
		requires(TurretAngle.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(Climber.GetInstance());
        
        placeGear = new DumbGearAuto();
        backMore = new AutoDriveStraightToPoint();
        faceTarget = new AutoTurn();
        shoot = new AutoDumbShoot();
        closeToTarget = new AutoDriveStraightToPoint();
        
        addSequential(placeGear);
        if((OI.fieldSide.getSelected().equals("Red") && OI.gearStartPos.getSelected().equals("Left"))
        		|| (OI.fieldSide.getSelected().equals("Blue") && OI.gearStartPos.getSelected().equals("Right"))){
        	addSequential(backMore, 2);
        }
        addSequential(faceTarget, 5);
        addSequential(closeToTarget, 3);
        addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {
    	shoot.setParam(
    			Preferences.getInstance().getDouble("GearAnd10 Left Speed", 3600),
    			Preferences.getInstance().getDouble("GearAnd10 Right Speed", 3575)
    	);

    	backMore.SetParam(
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Distance", 12), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Thresh", 2), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Speed", .5), 
    			false
    	);
    	
    	closeToTarget.SetParam(
    			Preferences.getInstance().getDouble("GearAnd10 Towards Boiler Dist", 20),
    			Preferences.getInstance().getDouble("GearAnd10 Towards Boiler Thresh", 2), 
    			Preferences.getInstance().getDouble("GearAnd10 Towards Boiler Speed", 0.5), 
    			false);
    	
    	switch (OI.fieldSide.getSelected()) {
			case "Red":
				
				switch(OI.gearStartPos.getSelected()){
					case "Right":
				    	faceTarget.SetParam(-Preferences.getInstance().getDouble("GearAnd10 Turn Close", 20.0));
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
			    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Close", 20.0));
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
