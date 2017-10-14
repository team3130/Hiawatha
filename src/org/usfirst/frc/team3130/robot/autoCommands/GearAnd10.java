package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.commands.TurretToAngle;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearAnd10 extends CommandGroup {

	private DumbGearAuto 			 placeGear;
	private AutoDriveStraightToPoint backMore;
	private TurretToAngle			 turnToBoiler;
	private SmartShoot 				 shoot;
	private AutoDelay 				 wait;
	
    public GearAnd10() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        requires(Climber.GetInstance());
        
        placeGear = new DumbGearAuto();
        backMore = new AutoDriveStraightToPoint();
        turnToBoiler = new TurretToAngle();
        shoot = new SmartShoot();
        wait = new AutoDelay();
        
        addParallel(turnToBoiler, 1);
        addSequential(placeGear);
        if((OI.fieldSide.getSelected().equals("Red") && OI.gearStartPos.getSelected().equals("Left"))
        		|| (OI.fieldSide.getSelected().equals("Blue") && OI.gearStartPos.getSelected().equals("Right"))){
        	addSequential(backMore, 1.5);
        }
        addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {

    	backMore.SetParam(
    			-Preferences.getInstance().getDouble("GearAnd10 Extra Back Distance", 12), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Thresh", 2), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Speed", .5), 
    			false
    	);
    	
    	switch (OI.fieldSide.getSelected()) {
		case "Red":
		
			switch(OI.gearStartPos.getSelected()){
				case "Right":
					turnToBoiler.SetParam(- 15);
					break;
					
				case "Center":
					turnToBoiler.SetParam(65);
					break;
				
				case "Left":
					turnToBoiler.SetParam(15);
					break;
			}
		
		break;
		
		case "Blue":
		
			switch(OI.gearStartPos.getSelected()){
			case "Right":
				turnToBoiler.SetParam(15);
				break;
				
			case "Center":
				turnToBoiler.SetParam(-65);
				break;
			
			case "Left":
				turnToBoiler.SetParam(-15);
				break;
			}
		
		break;
	
	}
    }
}
