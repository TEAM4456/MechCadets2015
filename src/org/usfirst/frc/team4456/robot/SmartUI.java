package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.SDashUI.SDButton;
import org.usfirst.frc.team4456.robot.SDashUI.SDNumberR;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartUI
{
	SDButton buttonResetEncoder;
	SDNumberR numberRGyroValue;
	
	public SmartUI(Robot robot)
	{
		//on click, will reset encoder
		buttonResetEncoder = new SDButton("Reset Encoder", robot)
		{
			@Override
			public void performAction()
			{
				super.performAction();
				if(SmartDashboard.getBoolean(this.getKey()))
		    	{
		    		robot.gyro.reset();
		    		SmartDashboard.putBoolean(this.getKey(), false);
		    	}
			}
		};
		numberRGyroValue = new SDNumberR("Gyro Value")
		{
			@Override
			public void sendSDValue()
			{
				super.sendSDValue();
				SmartDashboard.putNumber("Gyro Value", robot.gyro.getAngle());
			}
		};
		
	}
	
	public void update()
	{
		buttonResetEncoder.update();
		numberRGyroValue.update();
	}

}
