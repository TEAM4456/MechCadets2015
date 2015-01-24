package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI
{
    public UI(Robot robot)
    {
    	// Encoder
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putBoolean("resetEncoder", false);
    	
    	// SmartDashboard.putNumber("pValue", robot.pValue);
    	// SmartDashboard.putBoolean("setPValue", false);
    	
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	// SmartDashboard.putNumber("PIDControllerGet", robot.pidController.get());
        System.out.println("UI running");
        
        // Button for whether or not we use a gyroscope
        SmartDashboard.putBoolean("Using Gyro", false);
        
        // Button for whether we use mechanum or tank
        SmartDashboard.putBoolean("Using Mechanum", true);
        
        //GYRO
        SmartDashboard.putNumber("gyroValue", robot.gyro.getAngle());
    	SmartDashboard.putNumber("gyroRate", robot.gyro.getRate());
    }
    
    public void update(Robot robot)
    {
    	// Enabled red/green light
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	// Sets pValue to the number in pValue widget
    	/*
    	if (SmartDashboard.getBoolean("setPValue"))
    	{
    		robot.pidController.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	*/
    	
    	// Resets encoder
    	if (SmartDashboard.getBoolean("resetEncoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("resetEncoder", false);
    	}
    	
    	robot.useGyro = SmartDashboard.getBoolean("Using Gyro");
    	robot.useMechanum = SmartDashboard.getBoolean("Using Mechanum");
    	
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	// SmartDashboard.putNumber("PIDController Get", robot.pidController.get());
    	
    	SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	
    	//GYRO
    	SmartDashboard.putNumber("gyroValue", robot.gyro.getAngle());
    	SmartDashboard.putNumber("gyroRate", robot.gyro.getRate());
    }
	
}
