package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.util.Util;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;

public class WinchLoader
{
	/*
	 * controls:
	 * LBumper, RBumper, LTrigger, RTrigger
	 */
	private CANTalon talon1;
	private boolean leftBumperPress, rightBumperPress;
	private int currentTargetIndex;
	
	public WinchLoader(int id)
	{
		talon1 = new CANTalon(id);
		talon1.changeControlMode(CANTalon.ControlMode.Position);
		talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon1.setPID(1.00, 0.000001, 0);
		talon1.set(talon1.get());  // don't move when started...
		//talon1.enableControl();
	}
	
	public int getCurrentTargetIndex()
	{
		return currentTargetIndex;
	}

	public void doWinchStuff(XBoxController controller)
	{
		// Left bumper lowers winch by one level
		boolean rawLeftBumperState = controller.getLBumper();
		if(rawLeftBumperState && !leftBumperPress)
		{
			leftBumperPress = true;
			this.lowerWinch();
		}
		else if(!rawLeftBumperState && leftBumperPress)
		{
			leftBumperPress = false;
		}
		else
		{
			/*
			 * This will trigger if the bumperBPress == true and rawLeftBumperState == true
			 * Or if they're both false
			 */
		}
		
		// Right bumper raises winch by one level
		boolean rawRightBumperState = controller.getRBumper();
		if(rawRightBumperState && !rightBumperPress)
		{
			rightBumperPress = true;
			this.raiseWinch();
		}
		else if(!rawRightBumperState && rightBumperPress)
		{
			rightBumperPress = false;
		}
		else
		{
			/*
			 * This will trigger if the bumperBPress == true and rawLeftBumperState == true
			 * Or if they're both false
			 */
		}
		
		talon1.set(talon1.getSetpoint() + (Constants.MAX_WINCH_NUDGE * controller.getAxisTriggers()));
		
		//System.out.println("fwd:" + forwardNudge + " rev:" + reverseNudge);
		
	}
	
	public double getWinchPosition()
	{
		return talon1.get();
	}
	
	/*
	 *  Raises winch to closest default winch position above it 
	 *  unless the current position is above a certain threshold. 
	 *  If so, it goes to the next highest position.
	 */
	private void raiseWinch()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex >= Constants.WINCH_LOADER_POSITIONS.length-1)
		{
			targetIndex = Constants.WINCH_LOADER_POSITIONS.length - 1;
		}
		else
		{
			targetIndex = closestIndex + 1;
		}
		//System.out.println("raise from: " + closestIndex + " @" + Constants.WINCH_POSITIONS[closestIndex] + " to " +  targetIndex + " @" + Constants.WINCH_POSITIONS[targetIndex] );
		talon1.set(Constants.WINCH_LOADER_POSITIONS[targetIndex]);
		this.currentTargetIndex = targetIndex;
	}
	
	private void raiseWinchMax()
	{
		talon1.set(Constants.WINCH_LOADER_POSITIONS[Constants.WINCH_LOADER_POSITIONS.length-1]);
		this.currentTargetIndex = Constants.WINCH_LOADER_POSITIONS.length-1;
	}
	
	/*
	 *  Lowers winch to closest default winch position below it 
	 *  unless the current position is above a certain threshold. 
	 *  If so, it goes to the next lowest position.
	 */
	private void lowerWinch()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex <= 0)
		{
			targetIndex = 0;
		}
		else
		{
			targetIndex = closestIndex-1;
		}
		talon1.set(Constants.WINCH_LOADER_POSITIONS[targetIndex]);
		//System.out.println("lower: " + closestIndex + " " + targetIndex );
		this.currentTargetIndex = targetIndex;
	}
	
	private void lowerWinchMin()
	{
		talon1.set(Constants.WINCH_LOADER_POSITIONS[0]);
		this.currentTargetIndex = 0;
	}
	
	private int findClosestPosition()
	{
		double currentPos = talon1.get();
		double closestDistance = 0;
		int closestIndex = 0;
		double highestPos = Constants.WINCH_LOADER_POSITIONS[Constants.WINCH_LOADER_POSITIONS.length-1];
		double lowestPos = Constants.WINCH_LOADER_POSITIONS[0];
		if(currentPos > highestPos)
		{
			return Constants.WINCH_LOADER_POSITIONS.length-1;
		}
		else if(currentPos < lowestPos)
		{
			return 0;
		}
		else
		{
			for(int i = 0; i < Constants.WINCH_LOADER_POSITIONS.length; i++)
			{
				double distance = Math.abs(currentPos - Constants.WINCH_LOADER_POSITIONS[i]);
				//System.out.println("distance:" + distance + " i:" + i + " w[i]:" + Constants.WINCH_POSITIONS[i]);
				if(distance < closestDistance || i == 0)
				{
					closestDistance = distance;
					closestIndex = i;
				}
			}
		}
		return closestIndex;
	}
	

	
}