package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.util.Util;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Class for the wheels on the chassis that drive the robot.
 * @author oom2013
 */
public class Driver
{
	RobotDrive robotDrive;
	private CANTalon talon1, talon2, talon3, talon4;
	private boolean buttonAPressed = false;
	private boolean autoStabilize;
	private float initialGyroVal, destGyroVal;
	/** 
	 * Takes in robot type and initializes talon controllers depending on the robot type.
	 * @param roboType RobotType to use. MAIN_ PRACTICE_ or BREADBOARD_BOT
	 * @author samega15
	 */
	public Driver(RobotType roboType, float gyroValue)
	{
		autoStabilize = true;
		initialGyroVal = destGyroVal = gyroValue;
		
		if(roboType != null)
		{
			talon1 = new CANTalon(roboType.idRL);
			talon2 = new CANTalon(roboType.idFL);
			talon3 = new CANTalon(roboType.idRR);
			talon4 = new CANTalon(roboType.idFR);
		}
		else
		{
			System.err.println("ERROR: Talon Controllers > RobotType\n"
							+ "Driver.java\n"
							+ "public Driver(RobotType roboType)");
		}
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		//order RL FL RR FR
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	/** 
	 * Constructor checks whether or not we are using the test robot and switches between the test motors and the robot motors
	 * @param useTest
	 * @author oom2013
	 */
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
			talon1 = new CANTalon(14);
			talon2 = new CANTalon(21);
			talon3 = new CANTalon(16);
			talon4 = new CANTalon(12);
			
			/*
			talon1 = new CANTalon(17);
			talon2 = new CANTalon(18);
			talon3 = new CANTalon(20);
			talon4 = new CANTalon(19);
			*/
		}
		
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	
	/**
	 *  Executes the Polar, Cartesian, or Tank method based on the useMechanum and useGyro booleans
	 * @param controller
	 * @param gyro
	 * @param robot
	 * @author oom2013
	 */
	public void drive(XBoxController controller, float gyroVal, Robot robot)
	{
		// Set speed control
		if(controller.getA() && !buttonAPressed)
		{
			buttonAPressed = true;
			
			if(robot.speedFactor == .70)
			{
				robot.speedFactor = 1;
			}
			else
			{
				robot.speedFactor = .70;
			}
		}
		if(!controller.getA())
		{
			buttonAPressed = false;
		}
		
		
		//drive
		if(robot.useMechanum)
		{
			if(robot.useGyro)
	    	{
	    		this.driveCartesian(controller, gyroVal, robot);
	    	}
	    	else
	    	{
	    		this.drivePolar(controller, robot);
	    	}
		}
		// Tank drive is most likely not needed at all - This method will never be called
		else
		{
			this.driveTank(controller, robot);
		}
	}
	
	public void driveRawPolar(double magnitude, double direction, double rotation)
	{
		robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
	}
	
	public void driveRawCartesian(double x, double y, double rotation, double gyroAngle)
	{
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
	}
	
	/**
	 *  This will be used if we do not have a gyroscope
	 * @param controller
	 * @author oom2013
	 */
	private void drivePolar(XBoxController controller, Robot robot)
	{
		// Parameters are Magnitude, Direction, Rotation
		// Arguments are the magnitude of the joysticks, the direction of the joysticks, and the value given by the right-stick x-value
		
		double rotAmount;
		double gyroDiff;
		
		if(this.autoStabilize)
		{
			//will determine rotation value in order to minimize strafe error.
			destGyroVal += controller.getAxisRStickX() * Constants.RS_GYRO_FACTOR_1;
			gyroDiff = robot.navx.getYaw() - destGyroVal;
			rotAmount = Math.min(1, Math.pow(gyroDiff, 3) * Constants.RS_GYRO_FACTOR_2);
			
			robotDrive.mecanumDrive_Polar(Util.lowerSensitivity(controller.getMagnitude(),robot),
										controller.getDirectionDegrees(),
										rotAmount*robot.speedFactor);
		}
		else
		{
			robotDrive.mecanumDrive_Polar(Util.lowerSensitivity(controller.getMagnitude(), robot), 
					    			controller.getDirectionDegrees(), 
					    			-1 * Util.lowerSensitivity(controller.getAxisRStickX(), robot));
		}
	}
	
	/**
	 * This will be used if we do have a gyroscope
	 * @param controller
	 * @param gyro
	 * @author oom2013
	 */
	private void driveCartesian(XBoxController controller, float gyroVal, Robot robot)
	{
		// Parameters are X, Y, Rotation, and Gyro Angle
		// Arguments are the values given by the left-stick x-value, left-stick y-value, right-stick x-value, and the angle produced by the gyroscope
		robotDrive.mecanumDrive_Cartesian(Util.lowerSensitivity(controller.getAxisLStickX(), robot),
				Util.lowerSensitivity(controller.getAxisLStickY(), robot),
				-1 * Util.lowerSensitivity(controller.getAxisRStickX(), robot),
    			gyroVal);
	}
	
	/**
	 * This will be used if we are using TankDrive instead of Mechanum,
	 * but will probably not be used
	 * @param controller
	 * @author oom2013
	 */
	private void driveTank(XBoxController controller, Robot robot)
	{
		robotDrive.tankDrive(Util.lowerSensitivity(controller.getAxisLStickY(), robot),
				-1 * Util.lowerSensitivity(controller.getAxisRStickY(), robot));
	}
	
}