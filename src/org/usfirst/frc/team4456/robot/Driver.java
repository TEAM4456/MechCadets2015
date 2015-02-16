package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	private CANTalon talon1, talon2, talon3, talon4;

	// Constructor checks whether or not we are using the test robot and switches between the test motors and the robot motors
	public Driver(boolean useTest)
	{
		if(useTest)
		{
			talon1 = new CANTalon(11);
			talon2 = new CANTalon(10);
			talon3 = new CANTalon(15);
			talon4 = new CANTalon(22);
		}
		else
		{
			talon1 = new CANTalon(17);
			talon2 = new CANTalon(18);
			talon3 = new CANTalon(20);
			talon4 = new CANTalon(19);
		}
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	
	// Executes the Polar, Cartesian, or Tank method based on the useMechanum and useGyro booleans
	public void drive(XBoxController controller, Gyro gyro, Robot robot)
	{
		if(robot.useMechanum)
		{
			if(robot.useGyro)
	    	{
	    		this.driveCartesian(controller, gyro);
	    	}
	    	else
	    	{
	    		this.drivePolar(controller);
	    	}
		}
		else
		{
			this.driveTank(controller);
		}
	}
	
	// This will be used if we do not have a gyroscope
	private void drivePolar(XBoxController controller)
	{
		// Parameters are Magnitude, Direction, Rotation
		// Arguments are the magnitude of the joysticks, the direction of the joysticks, and the value given by the right-stick x-value
		robotDrive.mecanumDrive_Polar(lowerSensitivity(controller.getMagnitude()), 
    			controller.getDirectionDegrees(), 
    			lowerSensitivity(controller.getAxisRStickX()));
	}
	
	// This will be used if we do have a gyroscope
	private void driveCartesian(XBoxController controller, Gyro gyro)
	{
		// Parameters are X, Y, Rotation, and Gyro Angle
		// Arguments are the values given by the left-stick x-value, left-stick y-value, right-stick x-value, and the angle produced by the gyroscope
		robotDrive.mecanumDrive_Cartesian(lowerSensitivity(controller.getAxisLStickX()),
				lowerSensitivity(controller.getAxisLStickY()),
				lowerSensitivity(controller.getAxisRStickX()),
    			gyro.getAngle());
	}
	
	// This will be used if we are using TankDrive instead of Mechanum
	private void driveTank(XBoxController controller)
	{
		robotDrive.tankDrive(lowerSensitivity(controller.getAxisLStickY()),
				lowerSensitivity(controller.getAxisRStickY()));
	}
	
	// This sets the sensitivity exponentially
	private double lowerSensitivity(double value)
	{
		// The value should be from 0 to 1, so it makes an exponential curve
		// This method can be used by the various drive methods
		value = Math.pow(value, 3);
		if(value > 1)
		{
			value = 1;
		}
		if(value < -1)
		{
			value = -1;
		}
		return value;
	}
}