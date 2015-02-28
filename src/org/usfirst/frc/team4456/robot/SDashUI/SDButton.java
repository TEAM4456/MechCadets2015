package org.usfirst.frc.team4456.robot.SDashUI;

import org.usfirst.frc.team4456.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDButton implements SDElement
{
	private String key;
	private Robot mainRobot;
	
	/**
	 * constructs a SmartDashboard button object.
	 * Must override performAction() in order for the update() function to work.
	 * @param key the SmartDashboard key for this button
	 * @param robot the main robot
	 */
	public SDButton(String key, Robot robot)
	{
		this.key = key;
		
		//this will point to the main robot.
		this.mainRobot = robot;
	}
	
	/**
	 * gets this button's key
	 * @return this button's key
	 */
	@Override
	public String getKey()
	{
		return key;
	}
	
	/**
	 * Checks to see if button is pressed. If so, performs action.
	 * Remember to override performAction before using.
	 * This should be called in the UI update function
	 */
	public void update()
	{
		if (SmartDashboard.getBoolean(key))
    	{
    		this.performAction();
    		SmartDashboard.putBoolean("key", false);
    	}
	}
	
	/**
	 * Performs action when button is pressed.
	 * Stub should be overridden.
	 */
	public void performAction()
	{
		
	}

}