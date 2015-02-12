package org.usfirst.frc.team4456.robot.SDashUI;

import org.usfirst.frc.team4456.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDButton implements SDElement
{
	private String key;
	private Robot robot;
	
	public SDButton(String key, Robot robot)
	{
		this.key = key;
		
		//this will point to the main robot.
		this.robot = robot;
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
	 * Remember to override performAction before using
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
	 * Should be overridden.
	 */
	public void performAction()
	{}

}
