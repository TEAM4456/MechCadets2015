package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI
{
    public UI(Robot robot)
    {
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	// Encoder
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putBoolean("resetEncoder", false);
    	
    	// PID
    	SmartDashboard.putNumber("pValue", robot.pValue);
    	SmartDashboard.putBoolean("setPValue", false);
    	SmartDashboard.putNumber("PIDControllerGet", robot.driver.talon2.get());
    	
        System.out.println("UI running");
        
        // Button for whether or not we use a gyroscope
        SmartDashboard.putBoolean("Using Gyro", false);
        
        // Gyro Values
        SmartDashboard.putNumber("gyroValue", get360Angle(robot.gyro.getAngle()));
    	SmartDashboard.putNumber("gyroRate", robot.gyro.getRate());
    	SmartDashboard.putBoolean("Reset Gyro", false);
        
        // Button for whether we use Mechanum or Tank
        SmartDashboard.putBoolean("Using Mechanum", true);
        
        // Lidar Values 
        SmartDashboard.putNumber("PID Lidar Get", robot.lidar.pidGet());
    	SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    }
    
    public void update(Robot robot)
    {
    	// Enabled red/green light
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	// Resets encoder
    	if (SmartDashboard.getBoolean("resetEncoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("resetEncoder", false);
    	}
    	
    	// Encoder Values
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	
    	// Sets pValue to the number in pValue widget
    	if (SmartDashboard.getBoolean("setPValue"))
    	{
    		robot.driver.talon2.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	
    	// Toggles Gyro and Mechanum Buttons
    	robot.useGyro = SmartDashboard.getBoolean("Using Gyro");
    	robot.useMechanum = SmartDashboard.getBoolean("Using Mechanum");
    	
    	SmartDashboard.putNumber("PIDController Get", robot.driver.talon2.get());
    	
    	// Gyro Values
    	SmartDashboard.putNumber("gyroValue", robot.gyro.getAngle());
    	SmartDashboard.putNumber("gyroRate", robot.gyro.getRate());
    	
    	if(SmartDashboard.getBoolean("Reset Gyro"))
    	{
    		robot.gyro.reset();
    		SmartDashboard.putBoolean("Reset Gyro", false);
    	}
    	
    	// Lidar
    	SmartDashboard.putNumber("PID Lidar Get", robot.lidar.pidGet());
    	SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    }
    
    // This will just set an angle in between 0 and 360 to make it easier for the user to understand
    private double get360Angle(double value)
    {
    	while((value < 0 || value > 360))
    	{
    		if(value < 0)
    		{
    			value = value + 360;
    		}
    		if(value > 360)
    		{
    			value = value - 360;
    		}
    	}
    	return value;
    }
	
}