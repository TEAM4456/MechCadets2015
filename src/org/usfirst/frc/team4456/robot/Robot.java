package org.usfirst.frc.team4456.robot;

import java.util.Date;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	Joystick oldXboxController;
	XBoxController xboxController;
	Driver driver;
	WinchLoader winchLoader;
	Ladder ladder;
	Gyro gyro;
	Encoder encoder;
	UI ui;
	//SmartUI ui;
	DigitalInput limitSwitch;
	ADXL345_I2C accelerometer;
	Lidar lidar;
	Vision vision;
	UltrasonicSensor ultrasonic;
	SerialPort serial;
	Lidar arduinoLidar;
	PIDController pidController;
	
	double pValue;
	boolean useGyro, useMechanum;


	boolean buttonYPress = false;
	
    public void robotInit()
    {
    	// Driver init
    	driver = new Driver(true);
    	
    	ladder = new Ladder(0, Constants.piston1Port1, Constants.piston1Port2, Constants.piston2Port1, Constants.piston2Port2);
    	winchLoader = new WinchLoader(13);
    	
    	// Gyro init 
    	gyro = new Gyro(0);
    	
    	// Mechanum and Gyro booleans for Driver
    	useMechanum = true;
    	useGyro = false;
    	
    	// Controller init
    	oldXboxController = new Joystick(1);
    	xboxController = new XBoxController(1);
    	
    	// Encoder init
    	encoder = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
        encoder.setDistancePerPulse(1.0/360);
    	
        // Lidar init
    	//lidar = new LidarBackup(Port.kMXP);
    	
    	// Serial init
    	serial = new SerialPort(9600,SerialPort.Port.kUSB);
    	
    	// UI init
    	ui = new UI(this);
    	//ui = new SmartUI(this);

    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// Vision init
    	vision = new Vision();
    	
    	// Ultrasonic Sensor init
    	ultrasonic = new UltrasonicSensor(1);
    	
    	lidar = new Lidar(this);
    	
    	System.out.println("Robot Init successful: " + "");
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
    	
    	winchLoader.doWinchStuff(xboxController);
    	
    	lidar.update(this);
    	ladder.cycle(xboxController);
    	// vision.cycle();
    	
    	/*
    	if (xboxController.getB())
    	{
    		buttonBPress = true;
    	}
    	if (buttonBPress && !xboxController.getB())
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
