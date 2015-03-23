package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand2 extends Command
{
	Robot robot;
	boolean isFinished = false;
	double initialDisplacement;
	int hookPositionsLength = Constants.HOOK_LOADER_POSITIONS.length;
	
	public AutonomousCommand2(Robot robot)
	{
		this.robot = robot;
	}
	
	//method is called 1st time the command runs
	@Override
	protected void initialize()
	{
		System.out.println("Running AutoCommand2");
		initialDisplacement = robot.navx.getDisplacementY();
		robot.hooks.setIndex(hookPositionsLength - 2);
	}
	
	//periodically called until command finishes
	@Override
	protected void execute()
	{
		isFinished = (Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 2);
		if(robot.hooks.getWinchPosition() < Constants.HOOK_LOADER_POSITIONS[hookPositionsLength - 2])
			robot.driver.driveRawPolar(.4, 180, 0);
	}
	
	//returns true if the command is finished running
	@Override
	protected boolean isFinished()
	{
		return isFinished;
	}
	
	//called when command ends w/o interruption
	@Override
	protected void end()
	{
	}
	
	//called when command is interupted
	@Override
	protected void interrupted()
	{		
	}

}
