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
    	//encoder
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
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	if (SmartDashboard.getBoolean("setPValue"))
    	{
    		robot.pidController.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	
    	if (SmartDashboard.getBoolean("resetEncoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("resetEncoder", false);
    	}
    	
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putNumber("PIDController Get", robot.pidController.get());
    	
    	//!!!
    	//TODO gotta figure lidar out
    	byte[] byteArray = {1, 2, 3};
    	System.out.println("lidar value: " + robot.lidar.read(1, 1, byteArray));
    	
    }
	
}
