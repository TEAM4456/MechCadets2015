package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	
	public Driver(int FL, int RL, int FR, int RR) //FrontLeft RearLeft FrontRear RearRight
	{
		robotDrive = new RobotDrive(FL, RL, FR, RR);
	}
	
	public void drivePolar(double m, double d, double r) //magnitude, direction, rotation
	{
		robotDrive.mecanumDrive_Polar(m, d, r);
	}
	
	public void driveCartesian(double x, double y, double r, double g)
	{
		robotDrive.mecanumDrive_Cartesian(x, y, r, g);
	}
}
