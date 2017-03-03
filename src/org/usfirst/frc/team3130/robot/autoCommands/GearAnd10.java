package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.Robot;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsLeft;
import org.usfirst.frc.team3130.robot.subsystems.ShooterWheelsRight;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsLeft;
import org.usfirst.frc.team3130.robot.subsystems.WheelSpeedCalculationsRight;

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
	
    public GearAnd10() {
		requires(Chassis.GetInstance());
		requires(Robot.bcGearPinch);
		requires(Robot.bcGearLift);
        requires(ShooterWheelsRight.GetInstance());
        requires(ShooterWheelsLeft.GetInstance());
        requires(Robot.btHopper);
        requires(Robot.btRightIndex);
        requires(Robot.btLeftIndex);
        requires(WheelSpeedCalculationsLeft.GetInstance());
        requires(WheelSpeedCalculationsRight.GetInstance());
        
        placeGear = new DumbGearAuto();
        backMore = new AutoDriveStraightToPoint();
        faceTarget = new AutoTurn();
        shoot = new SmartShoot();
        
        addSequential(placeGear);
        if((OI.fieldSide.getSelected().equals("Red") && OI.gearStartPos.getSelected().equals("Left"))
        		|| (OI.fieldSide.getSelected().equals("Blue") && OI.gearStartPos.getSelected().equals("Right"))){
        	addSequential(backMore, 2);
        }
        addSequential(faceTarget, 2);
        addSequential(shoot);
    }
    
    @Override
    protected void initialize()
    {
    	backMore.SetParam(
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Distance", 12), 
    			Preferences.getInstance().getDouble("GearAnd10 Extra Back Thresh", 2), 
    			0, 
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
				    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Center", 15));
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
			    	faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Center", 15));
					break;
					
					case "Right":
					faceTarget.SetParam(Preferences.getInstance().getDouble("GearAnd10 Turn Far", 15));
						break;
				}
			break;
		}
    }
}
