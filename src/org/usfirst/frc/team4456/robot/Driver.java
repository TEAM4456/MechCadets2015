package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	
	public Driver(int FL, int RL, int FR, int RR) //FrontLeft, RearLeft, FrontRear, RearRight
	{
		robotDrive = new RobotDrive(FL, RL, FR, RR);
	}
	
	// This will be used if we do not have a gyroscope
	public void drivePolar(double m, double d, double r) //magnitude, direction, rotation
	{
		robotDrive.mecanumDrive_Polar(m, d, r);
	}
	
	// This will be used if we do have a gyroscope
	public void driveCartesian(double xAxis, double yAxis, double r, double g) // x, y, rotation, gyroAngle
	{
		robotDrive.mecanumDrive_Cartesian(xAxis, yAxis, r, g);
	}
	
	/*
	public void driveTank(double xAxis, double yAxis, double r, double g) // x, y, rotation, gyroAngle
	{
		robotDrive.tankDrive(leftValue, rightValue);
	}
	*/
}
