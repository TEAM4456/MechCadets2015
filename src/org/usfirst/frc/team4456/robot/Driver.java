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
	private CANTalon talon5;

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
		
		talon5 = new CANTalon(13);
		
		talon5.changeControlMode(CANTalon.ControlMode.Position);
		talon5.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		talon5.setPID(.5, 0, 0);
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	//Talon5 GetSet
	
	public double getMotorDistance()
	{
		return talon5.getPosition();
	}

	public void enableMotor()
	{
		talon5.set(.4);
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
		// Parameters are Magnitude, Direction, Rotation
		// Arguments are the magnitude of the joysticks, the direction of the joysticks, and the value given by the right-stick x-value
		robotDrive.mecanumDrive_Polar(lowerSensitivity(controller.getMagnitude()), 
    			controller.getDirectionDegrees(), 
    			lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_X)));
	}
	
	// This will be used if we do have a gyroscope
	private void driveCartesian(Joystick controller, Gyro gyro)
	{
		// Parameters are X, Y, Rotation, and Gyro Angle
		// Arguments are the values given by the left-stick x-value, left-stick y-value, right-stick x-value, and the angle produced by the gyroscope
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
