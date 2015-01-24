package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	
	public Driver(int FL, int RL, int FR, int RR) //FrontLeft, RearLeft, FrontRear, RearRight
	{
		robotDrive = new RobotDrive(FL, RL, FR, RR);
	}
	
	// Drive method executes both Polar and Cartesian methods based on the useGyro boolean
	public void drive(Joystick controller, Gyro gyro, Robot robot)
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
	
	// This 
	private double lowerSensitivity(double value)
	{
		if(Math.abs(value) < .1)
		{
			value = 0;
		}
		return value;
	}
}
