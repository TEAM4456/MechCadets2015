package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;

public class UI
{

	private DriverStation outputBox;
	private int printCounter;
    
    public UI(Robot robot)
    {
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putNumber("Hello Santa", 2.7);
    	SmartDashboard.putBoolean("test", false);
        outputBox = DriverStation.getInstance();
        printCounter = 0;
        System.out.println("UI running");
    }
    
    public void update(Robot robot)
    {
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	if (SmartDashboard.getBoolean("test"))
    	{
    		System.out.println("test running");
    	}
    }
	
}
