package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.autoCommands.AutoDelay;
import org.usfirst.frc.team3130.robot.autoCommands.AutoDriveStraightToPoint;
import org.usfirst.frc.team3130.robot.continuousDrive.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestContinuous extends CommandGroup {

	private ContDrive drive1;
	private ContTurnDist turnDist;
	private ContDrive drive2;
	private ContTurnHeading turnHeading;
	private AutoDelay wait;
	
    public TestContinuous() {
        requires(Chassis.GetInstance());
        
        drive1 = new ContDrive();
        turnDist = new ContTurnDist();
        drive2 = new ContDrive();
        turnHeading = new ContTurnHeading();
        wait = new AutoDelay();
        
        addSequential(drive1);
        addSequential(turnDist);
        addSequential(wait, 3);
        addSequential(drive2);
        addSequential(turnHeading);
    }
    
    @Override
    protected void initialize()
    {
    	drive1.SetParam(.6, 30);
    	
    	drive2.SetParam(.6, 30);
    	
    	turnDist.SetParam(1.2, -90*Math.PI/180f);
    	
    	turnHeading.SetParam(1.2, -90*Math.PI/180f);
    }
}
