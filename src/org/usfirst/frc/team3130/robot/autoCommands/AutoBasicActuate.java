package org.usfirst.frc.team3130.robot.autoCommands;

import org.usfirst.frc.team3130.robot.subsystems.BasicCylinder;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBasicActuate extends Command {

	private BasicCylinder cylinder;
	private boolean extend;
	
    public AutoBasicActuate(BasicCylinder cylinder, boolean extend) {
        requires(cylinder);
        this.cylinder = cylinder;
        this.extend = extend;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cylinder.actuate(extend);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
