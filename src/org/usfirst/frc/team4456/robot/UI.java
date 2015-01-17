package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;

public class UI
{

	private DriverStation outputBox;
	private int printCounter;
    
    public UI()
    {
    	SmartDashboard.putNumber("Hello Santa", 2.7);
    	SmartDashboard.putBoolean("test", false);
        outputBox = DriverStation.getInstance();
        printCounter = 0;
        System.out.println("UI running");
    }
    
    public void update(RobotBase robotBase)
    {
    	SmartDashboard.putBoolean("Enabled", robotBase.isEnabled());
    	if (SmartDashboard.getBoolean("test"))
    	{
    		System.out.println("test running");
    	}
    }
	
}
