package org.usfirst.frc.team4456.robot;

public class Constants
{
	// Defines XBox Controls
	/*
	 * The left thumbstick will drive the robot
	 * The right thumbstick will rotate the robot
	 */
	public static final int button_A = 1;
	public static final int button_B = 2;
	public static final int button_X = 3;
	public static final int button_Y = 4;
	public static final int button_leftBumper = 5;
	public static final int button_rightBumper = 6;
	public static final int button_Back = 7;
	public static final int button_Start = 8;
	public static final int button_leftStick = 9;
	public static final int button_rightStick = 10;
	public static final int axis_leftStick_X = 0;
	public static final int axis_leftStick_Y = 1;
	public static final int axis_leftTrigger = 2;
	public static final int axis_rightTrigger = 3;
	public static final int axis_rightStick_X = 4;
	public static final int axis_rightStick_Y = 5;
	public static final int axis_dPad_X = 6;

	// Lidar Stuff
	public static final int LIDAR_ADDR = 0x62;
	public static final int LIDAR_CONFIG_REGISTER = 0x00;
	public static final int LIDAR_DISTANCE_REGISTER = 0x8f;
	
	public static final double ULTRASONIC_FACTOR_VOLTS = 40.2969;
	
	public static final String filePathRoborio = "/home/lvuser/";
}