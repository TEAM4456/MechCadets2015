package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand1 extends Command
{
	Robot robot;
	boolean isFinished = false;
	double initialDisplacement;
	
	public AutonomousCommand1(Robot robot)
	{
		this.robot = robot;
	}
	
	//method is called 1st time the command runs
	@Override
	protected void initialize()
	{
		System.out.println("Running AutoCommand1");
		initialDisplacement = robot.navx.getDisplacementY();
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 2);
	}
	
	//periodically called until command finishes
	@Override
	protected void execute()
	{
		isFinished = (Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 10);
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
