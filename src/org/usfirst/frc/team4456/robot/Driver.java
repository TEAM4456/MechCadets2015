package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	CANTalon talon1, talon2, talon3, talon4;
	
	public Driver()
	{
		talon1 = new CANTalon(11);
		talon2 = new CANTalon(13);
		talon3 = new CANTalon(15);
		talon4 = new CANTalon(22);
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	// Executes the Polar, Cartesian, or Tank method based on the useMechanum and useGyro booleans
	public void drive(Joystick controller, Gyro gyro, Robot robot)
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
	private void drivePolar(Joystick controller)
	{
		// Magnitude, Direction, Rotation
		robotDrive.mecanumDrive_Polar(lowerSensitivity(controller.getMagnitude()), 
    			controller.getDirectionDegrees(), 
    			lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_X)));
	}
	
	// This will be used if we do have a gyroscope
	private void driveCartesian(Joystick controller, Gyro gyro)
	{
		// X, Y, Rotation, Gyro Angle
		robotDrive.mecanumDrive_Cartesian(lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_X)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_Y)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_X)),
    			gyro.getAngle());	
	}
	
	// This will be used if we are using TankDrive instead of Mechanum
	private void driveTank(Joystick controller)
	{
		robotDrive.tankDrive(lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_Y)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_Y)));
	}
	
	// This sets the sensitivity exponentially
	private double lowerSensitivity(double value)
	{
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
