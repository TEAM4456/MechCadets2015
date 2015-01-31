package org.usfirst.frc.team4456.robot;

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
	PIDController pidController;
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
    	
    	// UI init
    	ui = new UI(this);

    	// Accelerometer init
    	accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
    	
    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// Lidar init
    	lidar = new Lidar();
    	
    	// PID init
    	// pValue = -.5;
    	// pidController = new PIDController(pValue, 0, 0, encoder, testMotor);
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
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
    }
    
    public void teleopPeriodic()
    {
    	ui.update(this);
    	
    	/*
    	 * Switches between Cartesian and Polar based on whether or 
    	 * not we are using a gyro.
    	 */
    	driver.drive(xboxController, gyro, this);
    	
    	vision.cycle();
    	
    	if (xboxController.getRawButton(Constants.button_B))
    	{
    		buttonBPress = true;
    	}
    	
    	if (buttonBPress == true && !xboxController.getRawButton(Constants.button_B))
    	{
    		vision.writeThresholdImg();
    		buttonBPress = false;
    	}
    	
    	if (xboxController.getRawButton(Constants.button_A))
    	{
    		xboxController.setRumble(Joystick.RumbleType.kLeftRumble, 1);
    		xboxController.setRumble(Joystick.RumbleType.kRightRumble, 1);
    	}
    }
    
    public void disabledPeriodic()
    {
    	super.disabledPeriodic();
    	ui.update(this);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	super.testPeriodic();
    }
    
}
