package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.SDashUI.SDButton;
import org.usfirst.frc.team4456.robot.SDashUI.SDNumberR;

/**
 * New Smart Dashboard class
 * @author samega15
 */
public class SmartUI
{
	SDButton buttonResetEncoder;
	SDNumberR numberGyroValue, currentMagnitude, numberTestLidar;
	
	public SmartUI(Robot robot)
	{
		// On click, will reset encoder
		buttonResetEncoder = new SDButton("Reset Encoder", robot)
		{
			@Override
			public void performAction()
			{
		    	robot.gyro.reset();
			}
		};
		numberGyroValue = new SDNumberR("Gyro Value")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.gyro.getAngle());
			}
		};
		currentMagnitude = new SDNumberR("Current Magnitude")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.xboxController.getMagnitude());
			}
		};
		numberTestLidar = new SDNumberR("Test Lidar")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.lidar.getDistance());
			}
		};
		
	}
	
	public void update(Robot robot)
	{
		buttonResetEncoder.update();
		numberGyroValue.update();
		currentMagnitude.update();
		numberTestLidar.update();
	}

}