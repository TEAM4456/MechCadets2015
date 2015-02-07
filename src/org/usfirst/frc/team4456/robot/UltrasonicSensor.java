package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicSensor
{
	//TODO change this to private when done testing
	public AnalogInput ultrasonicSensor;
	
	public UltrasonicSensor(int channel)
	{
		ultrasonicSensor = new AnalogInput(channel);
	}
	
	public String getValues()
	{
		// Gets Values
		return  "ULTRASONIC--------------------" +
				"\ngetValue():" + ultrasonicSensor.getValue() +
				"\ngetVoltage():" + ultrasonicSensor.getVoltage();
	}
}