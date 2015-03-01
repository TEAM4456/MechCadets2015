package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.SDashUI.SDButton;
import org.usfirst.frc.team4456.robot.SDashUI.SDNumberR;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * New Smart Dashboard class
 * @author samega15
 */
public class SmartUI
{
	SDButton buttonResetEncoder, buttonSetPID;
	SDNumberR currentMagnitude, cartesianXValue, cartesianYValue, currentRotation, encoderDistance, encoderCount, pValue, numberGyroValue, numberTestLidar;
	
	public SmartUI(Robot robot)
	{
		// Driver Values
		currentMagnitude = new SDNumberR("Current Magnitude")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.xboxController.getMagnitude());
			}
		};
		cartesianXValue = new SDNumberR("Cartesian X Value")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.xboxController.getAxisLStickX());
			}
		};
		cartesianYValue = new SDNumberR("Cartesian Y Value")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.xboxController.getAxisLStickY());
			}
		};
		currentRotation = new SDNumberR("Current Rotation")
		{
			@Override
			public void update()
			{
				sendSDValue(robot.xboxController.getAxisRStickX());
			}
		};
		
		// Encoder Values
    	encoderDistance = new SDNumberR("Encoder Distance")
    	{
    		@Override
    		public void update()
    		{
    			sendSDValue(robot.encoder.getDistance());
    		}
    	};
    	encoderCount = new SDNumberR("Encoder Count")
    	{
    		@Override
    		public void update()
    		{
    			sendSDValue(robot.encoder.get());
    		}
    	};
		// On click, will reset encoder
		buttonResetEncoder = new SDButton("Reset Encoder", robot)
		{
			@Override
			public void performAction()
			{
		    	robot.gyro.reset();
			}
		};
		
		// PID Values
		pValue = new SDNumberR("P Value")
    	{
    		@Override
    		public void update()
    		{
    			sendSDValue(robot.pValue);
    		}
    	};
    	
    	encoderCount = new SDNumberR("Encoder Count")
    	{
    		@Override
    		public void update()
    		{
    			sendSDValue(robot.encoder.get());
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
		currentMagnitude.update();
		cartesianXValue.update();
		cartesianYValue.update();
		currentRotation.update();
		
		encoderDistance.update();
		encoderCount.update();
		buttonResetEncoder.update();
		
		pValue.update();
		
		numberGyroValue.update();
		numberTestLidar.update();
	}

}