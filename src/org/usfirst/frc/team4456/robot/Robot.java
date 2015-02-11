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
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;
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
	LidarBackup lidar;
	Vision vision;
	UltrasonicSensor ultrasonic;
	SerialPort serial;
	SLidar arduinoLidar;
	
	double pValue;
	boolean useGyro, useMechanum;

    public void robotInit()
    {
    	// Driver init
    	driver = new Driver(true);
    	
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
    	lidar = new LidarBackup(Port.kMXP);
    	
    	// Serial init
    	serial = new SerialPort(9600,SerialPort.Port.kUSB);
    	
    	// UI init
    	ui = new UI(this);

    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// Vision init
    	vision = new Vision();
    	
    	// Ultrasonic Sensor init
    	ultrasonic = new UltrasonicSensor(1);
    	
    	arduinoLidar = new SLidar(this);
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
    }
    
    public void disabledInit()
    {
    	super.disabledInit();
    	lidar.stop();
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
    	/* 
    	 * We could make some code that is executed by both the autonomous mode 
    	 * and the teleop mode so that we can continue the autonomous part
    	 */
    	super.autonomousPeriodic();
    }
    
    //----------------
    //TELEOP
    //----------------
    public void teleopInit()
    {
    	super.teleopInit();
    	lidar.start(1000);
    	gyro.reset();
    }
    
    public void teleopPeriodic()
    {
    	ui.update(this);
    	
    	lidar.getDistance();
    	
    	/*
    	 * Switches between Mechanum and Tank based on what wheels we are using.
    	 * It also switches between Cartesian and Polar Mechanum Drives based on 
    	 * whether or not we are using a gyro.
    	 */
    	driver.drive(xboxController, gyro, this);
    	
    	arduinoLidar.update(this);
    	
    	// vision.cycle();
    	
    	/*
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
    	*/
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
