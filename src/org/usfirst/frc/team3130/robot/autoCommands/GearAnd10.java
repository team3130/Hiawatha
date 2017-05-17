package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Climber;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearAnd10 extends CommandGroup {

	private DumbGearAuto placeGear;
	private AutoDriveStraightToPoint backMore;
	private AutoTurn faceTarget;
	private SmartShoot shoot;
	private CameraDrive closeToTarget;
	private AutoDelay wait;
	
    public GearAnd10() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        requires(ShooterWheelsRight.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(Robot.wscLeft);
        requires(Robot.wscRight);
        requires(Climber.GetInstance());
        
        placeGear = new DumbGearAuto();
        backMore = new AutoDriveStraightToPoint();
        faceTarget = new AutoTurn();
        shoot = new SmartShoot();
        closeToTarget = new CameraDrive();
        wait = new AutoDelay();
        
        addSequential(placeGear);
        if((OI.fieldSide.getSelected().equals("Red") && OI.gearStartPos.getSelected().equals("Left"))
        		|| (OI.fieldSide.getSelected().equals("Blue") && OI.gearStartPos.getSelected().equals("Right"))){
        	addSequential(backMore, 2);
        }
        addSequential(faceTarget, 2);
        addSequential(closeToTarget, 3);
        addSequential(wait, 1);
        addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {
    	shoot.setParam(Preferences.getInstance().getDouble("GearAnd10 Index Speed", 0.5));

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
				    	faceTarget.SetParam(-Preferences.getInstance().getDouble("GearAnd10 Turn Close", 15));
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
