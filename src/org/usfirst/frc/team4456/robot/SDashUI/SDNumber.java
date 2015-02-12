package org.usfirst.frc.team4456.robot.SDashUI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDNumber implements SDElement
{
	private String key;
	public SDNumber(String key)
	{
		this.key = key;
	}

	@Override
	public String getKey()
	{
		return key;
	}
	
	/**
	 * get value from Smart Dashboard.
	 * @return
	 */
	public double getSDValue()
	{
		return SmartDashboard.getNumber(key);
	}
	
	public void sendSDValue(double number)
	{
		SmartDashboard.putNumber(key,number);
	}
}
