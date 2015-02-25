package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.SDashUI.*;
import org.usfirst.frc.team4456.robot.util.Util;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.networktables2.server.NetworkTableServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI
{
    public UI(Robot robot)
    {
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getAxisLStickX());
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getAxisLStickY());
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getAxisRStickX());
    	
    	// Encoder
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putBoolean("Reset Encoder", false);
    	
    	// PID
    	SmartDashboard.putNumber("P Value", robot.pValue);
    	SmartDashboard.putBoolean("Set PID", false);
    	SmartDashboard.putNumber("Encoder Position", robot.winchLoader.getWinchPosition());
    	
        System.out.println("UI Running");
        
        // Button for whether or not we use a gyroscope
        SmartDashboard.putBoolean("Using Gyro", false);
        
        // Gyro Values
        SmartDashboard.putNumber("Gyro Value", Util.get360Angle(robot.gyro.getAngle()));
    	SmartDashboard.putNumber("Gyro Rate", robot.gyro.getRate());
    	SmartDashboard.putBoolean("Reset Gyro", false);
        
        // Button for whether we use Mechanum or Tank
        SmartDashboard.putBoolean("Using Mechanum", true);
        
        
        SmartDashboard.putData("talonPID", robot.pidController);
        
        // Lidar Values 
        // SmartDashboard.putNumber("Get PID Lidar", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    }
    
    public void update(Robot robot)
    {
    	// Enabled red/green light
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	//robot.pidController = (PIDController) SmartDashboard.getData("talonPID");
    	SmartDashboard.putData("talonPID", robot.pidController);
    	
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getAxisLStickX());
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getAxisLStickY());
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getAxisRStickX());
    	
    	// Resets encoder
    	if (SmartDashboard.getBoolean("Reset Encoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("Reset Encoder", false);
    	}
    	
    	// Encoder Values
    	SmartDashboard.putNumber("Encoder Distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder Count", robot.encoder.get());
    	
    	// Sets pValue to the number in pValue widget
    	
    	/*
    	if (SmartDashboard.getBoolean("Set P Value"))
    	{
    		robot.driver.talon2.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	*/
    	
    	// Toggles Gyro and Mechanum Buttons
    	robot.useGyro = SmartDashboard.getBoolean("Using Gyro");
    	robot.useMechanum = SmartDashboard.getBoolean("Using Mechanum");
    	
    	//SmartDashboard.putNumber("Get PIDController", robot.driver.talon2.get());
    	
    	// Gyro Values
    	SmartDashboard.putNumber("Gyro Value", robot.gyro.getAngle());
    	SmartDashboard.putNumber("Gyro Rate", robot.gyro.getRate());
    	
    	
    	if(SmartDashboard.getBoolean("Reset Gyro"))
    	{
    		robot.gyro.reset();
    		SmartDashboard.putBoolean("Reset Gyro", false);
    	}
    	
    	// Lidar
    	// SmartDashboard.putNumber("Get PID Lidar", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	
    	//ultrasonic and lidar
    	SmartDashboard.putNumber("Ultrasonic Value", robot.ultrasonic.getValueInches());
    	SmartDashboard.putNumber("Test Lidar", robot.lidar.getDistance());
    	
    	//talon encoder
    	SmartDashboard.putNumber("Talon Encoder Position", robot.winchLoader.getWinchPosition());
    	
    	// PID
    	SmartDashboard.putBoolean("Set P Value", false);
    	
    	SmartDashboard.putNumber("P Value", robot.pValue);
    	//SmartDashboard.putNumber("PIDControllerGet", robot.driver.talon2.get());
    }
}