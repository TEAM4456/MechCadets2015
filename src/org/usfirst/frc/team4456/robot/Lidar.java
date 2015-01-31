package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

public class Lidar
{
	private I2C i2cLidar;
	byte[] toSend = new byte[1];
	
	public Lidar()
	{
		i2cLidar = new I2C(I2C.Port.kOnboard, 0x62); // Lidar i2c address = 0x62
	}
	
	// Returns lidar distance in centimeters
	public int getDistance()
	{
		i2cLidar.write(0x00, 0x04);
		
		byte[] distanceMeasurements = new byte[2];
		int finalDistance;
		
		// This will get a 16-bit number from the lidar.
		// The number is split into 2 8-bit numbers(byte[] distanceMeasurements)
		i2cLidar.read(0x8f, 2, distanceMeasurements);
		
		// The 2 8-bit numbers are combined. the first is bitwise shifted to the left 8 times, and is added to the second.
		finalDistance = (distanceMeasurements[0] << 8) + distanceMeasurements[1];
		
		return finalDistance;
	}
}