package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI
{
	private String arduinoBuffer = "";
	private int arduinoBufferLength = 50;
	
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
        // SmartDashboard.putNumber("PID Lidar Get", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	arduinoBuffer = robot.serial.readString();
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
    	// SmartDashboard.putNumber("PID Lidar Get", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	
    	updateArduinoBuffer(robot.serial.readString());
    	//System.out.println(arduinoBuffer);
    	SmartDashboard.putNumber("Arduino", format(arduinoBuffer));
    	
    	//ultrasonic
    	SmartDashboard.putNumber("ultrasonic value", robot.ultrasonic.ultrasonicSensor.getValue());
    	SmartDashboard.putNumber("ultrasonic voltage", robot.ultrasonic.ultrasonicSensor.getVoltage());
    	SmartDashboard.putNumber("ultrasonic avg voltage", robot.ultrasonic.ultrasonicSensor.getAverageVoltage());
    	SmartDashboard.putNumber("ultrasonic avg value", robot.ultrasonic.ultrasonicSensor.getAverageValue());
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
    
    private double format(String string)
    {
    	String[] stringArray = string.split("\n");
    	double[] doubleArray = new double[stringArray.length];
    	for(int i = 0; i < stringArray.length; i++)
    	{
    		try 
    		{
        		doubleArray[i] = Double.parseDouble(stringArray[i]);
    		}
    		catch(Exception e)
    		{
    			doubleArray[i] = 1.0;
    		}
    	}
    	if(doubleArray.length < 2)
    	{
    		return 0;
    	}
    	return doubleArray[doubleArray.length-2];
    }
	
    private void updateArduinoBuffer(String newString)
    {
    	String tempString = arduinoBuffer + newString;
    	int len = Math.min(tempString.length(), arduinoBufferLength);
    	arduinoBuffer = tempString.substring(tempString.length() - len);
    }
    
}