package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class UI
{

	private DriverStation outputBox;
	private int printCounter;
    
    public UI()
    {
    	SmartDashboard.putNumber("Hello Santa", 2.7);
        outputBox = DriverStation.getInstance();
        printCounter = 0;
    }
	
}
