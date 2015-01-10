
package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	Joystick xboxController;
	
	Driver driver;
	
	Gyro gyro;
	
    public void robotInit()
    {
    	xboxController = new Joystick(1); //instantiate xbCtrlr for USB port 1
    	driver = new Driver(1, 2, 3, 4);
    	//gyro = new Gyro(1);
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
    }
    
    public void teleopInit()
    {
    	super.teleopInit();
    }
    
    public void disabledInit()
    {
    	super.disabledInit();
    }
    
    public void testInit()
    {
    	super.testInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
    	super.autonomousPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
    	driver.drivePolar(xboxController.getMagnitude(),
    			xboxController.getDirectionDegrees(),
    			xboxController.getRawAxis(Constants.axis_rightStick_X));
    	/*
    	driver.driveCartesian(xboxController.getRawAxis(Constants.axis_leftStick_X),
    			xboxController.getRawAxis(Constants.axis_leftStick_Y),
    			xboxController.getRawAxis(Constants.axis_rightStick_X),
    			gyro.getAngle());		
		*/
    }
    
    public void disabledPeriodic()
    {
    	super.disabledPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	super.testPeriodic();
    }
    
}
