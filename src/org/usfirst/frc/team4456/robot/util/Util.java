package org.usfirst.frc.team4456.robot.util;

/**
 * various utility functions for the robot.
 * @author MechCadets
 *
 */
public class Util
{
	
	/**
	 * calculates minimum
	 * @param a first number
	 * @param b second number
	 * @return minimum number
	 */
	public static double min(double a, double b)
	{
		return a>b ? b : a;
	}
	
	/**
	 * calculates maximum
	 * @param a first number
	 * @param b second number
	 * @return maximum number
	 */
	public static double max(double a, double b)
	{
		return a>b ? a : b;
	}
	
	/**
	 * meant for value with range 0 to 1
	 * reduces sensitivity for lower values
	 * @param value value
	 * @return value^3
	 */
	public static double lowerSensitivity(double value)
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
