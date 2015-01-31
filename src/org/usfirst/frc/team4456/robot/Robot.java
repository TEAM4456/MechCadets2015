package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

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
	Encoder encoder;
	UI ui;
	DigitalInput limitSwitch;
	ADXL345_I2C accelerometer;
	Lidar lidar;
	Vision vision;
	
	double pValue;
	boolean useGyro, useMechanum;

	boolean buttonBPress;

	
    public void robotInit()
    {
    	// Driver init
    	driver = new Driver();
    	
    	// Gyro init 

    	gyro = new Gyro(0);
    	
    	// Mechanum and Gyro booleans for Driver
    	useMechanum = true;
    	useGyro = false;
    	
    	// Controller init
    	xboxController = new Joystick(1);
    	
    	// Encoder init
    	encoder = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
        encoder.setDistancePerPulse(1.0/360);
    	
        // Lidar init
    	//lidar = new Lidar(Port.kOnboard);
    	lidar = new Lidar(Port.kMXP);
        
    	// UI init
    	ui = new UI(this);

    	// Accelerometer init
    	//accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
    	
    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// PID init
    	pValue = -.5;
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
    }
    
    public void disabledInit()
    {
    	super.disabledInit();
    	//lidar.stop();
    }
    
    public void testInit()
    {
    	super.testInit();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() // We could make some code that is executed by both the autonomous mode and the teleop mode so that we can continue the autonomous part
    {
    	super.autonomousPeriodic();
    }
    
    //----------------
    //TELEOP
    //----------------
    public void teleopInit()
    {
    	super.teleopInit();
    	lidar.start(500);
    }
    
    public void teleopPeriodic()
    {
    	ui.update(this);
    	
    	//lidar.getDistance();
    	
    	/*
    	 * Switches between Cartesian and Polar based on whether or 
    	 * not we are using a gyro.
    	 */
    	driver.drive(xboxController, gyro, this);
    	
    	// Mechanum and Gyro toggle temporary buttons
    	/*
    	if(xboxController.getRawButton(Constants.button_Y))
    	{
    		if(useMechanum)
    		{
    			useMechanum = false;
    		}
    		else
    		{
    			useMechanum = true;
    		}
    	}
    	if(xboxController.getRawButton(Constants.button_X))
    	{
    		if(useGyro)
    		{
    			useGyro = false;
    		}
    		else
    		{
    			useGyro = true;
    		}
    	}
    	*/
    	
    	// vision.cycle();
    	
    	if (xboxController.getRawButton(Constants.button_B))
    	{
    		buttonBPress = true;
    	}
    	
    	if (buttonBPress && !xboxController.getRawButton(Constants.button_B))
    	{
    		Timer.delay(0.005);
    		vision.writeThresholdImg();
    		buttonBPress = false;
    	}
    	
    }
    
    public void disabledPeriodic()
    {
    	super.disabledPeriodic();
    	//ui.update(this);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	super.testPeriodic();
    }
    
}
