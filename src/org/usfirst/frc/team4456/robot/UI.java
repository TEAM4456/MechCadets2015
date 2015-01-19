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
    	// Encoder
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putBoolean("resetEncoder", false);
    	
    	SmartDashboard.putNumber("pValue", robot.pValue);
    	SmartDashboard.putBoolean("setPValue", false);
    	
    	SmartDashboard.putNumber("PIDControllerGet", robot.pidController.get());
        outputBox = DriverStation.getInstance();
        printCounter = 0;
        System.out.println("UI running");
    }
    
    public void update(Robot robot)
    {
    	// Enabled red/green light
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	// Sets pValue to the number in pValue widget
    	if (SmartDashboard.getBoolean("setPValue"))
    	{
    		robot.pidController.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	
    	// Resets encoder
    	if (SmartDashboard.getBoolean("resetEncoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("resetEncoder", false);
    	}
    	
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putNumber("PIDController Get", robot.pidController.get());
    	
    	SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    }
	
}