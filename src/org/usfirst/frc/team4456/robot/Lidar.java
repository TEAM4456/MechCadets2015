package org.usfirst.frc.team4456.robot;

import java.util.TimerTask;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;

public class Lidar implements PIDSource
{
	private I2C i2cLidar;
	private byte[] distance;
	private java.util.Timer updater;
	/*
	public Lidar()
	{
		i2cLidar = new I2C(I2C.Port.kOnboard, 0x62); // Lidar i2c address = 0x62
	}
	*/
	public Lidar(Port port)
	{
		i2cLidar = new I2C(port, Constants.LIDAR_ADDR);
		distance = new byte[2];
		distance[0] = 0;
		distance[1] = 0;
		updater = new java.util.Timer();
	}
	
	public int getDistance()
	{
		return (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
	}
	
	public double pidGet()
	{
		return (double)getDistance();
	}
	
	public void start()
	{
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	public void start(int period)
	{
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period);
	}
	
	public void stop()
	{
		updater.cancel();
		updater = new java.util.Timer();
	}
	
	public void update()
	{
		long ms0 = System.nanoTime();
		i2cLidar.write(Constants.LIDAR_CONFIG_REGISTER,  0x04);
		long ms1 = System.nanoTime();
		System.out.println("write time: " + (ms1-ms0));
		Timer.delay(0.04);
		i2cLidar.read(Constants.LIDAR_DISTANCE_REGISTER, 2, distance);
		long ms2 = System.nanoTime();
		System.out.println("read time: " + (ms2-ms1));
	    Timer.delay(0.005);
		System.out.println("Updating\tdistance[0]: " + distance[0] + "\tdistance[1]: " + distance[1]);
		//System.out.println(getDistance());
	}
	
	private class LIDARUpdater extends TimerTask
	{
		public void run()
		{
			while(true)
			{
				update();
				try
				{
					Thread.sleep(10);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	// Returns lidar distance in centimeters
	/*
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
	*/
}