package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class UI
{

	private SmartDashboard smartdashboard;
	private DriverStation outputBox;
	private int printCounter;
    
    public UI()
    {
            outputBox = DriverStation.getInstance();
            printCounter = 0;
    }
	
}
