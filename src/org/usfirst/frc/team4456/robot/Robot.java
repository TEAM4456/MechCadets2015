package org.usfirst.frc.team4456.robot;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	//Change the current root used HERE
	RobotType roboType = RobotType.PRACTICE_BOT;
	
	XBoxController xboxController;
	
	Driver driver;
	Hooks hooks;
	Ladder ladder;
	Gyro gyro;
	Encoder encoder;
	DigitalInput limitSwitch;
	ADXL345_I2C accelerometer;
	UltrasonicSensor ultrasonic;
	Lidar lidar;
	Compressor compressor;
	
	AHRS navx;
	
	UI ui;
	SmartUI smartUi;
	//Vision vision;
	SerialPort serialUSB, serialPortMXP;
	PIDController pidController;
	Talon talon;
	
	double pValue;
	boolean useGyro, useMechanum;
	
    public void robotInit()
    {
    	// Driver init
    	driver = new Driver(roboType);
    	
    	// Hooks and Ladder init
    	ladder = new Ladder(11, Constants.piston1Port1, Constants.piston1Port2, Constants.piston2Port1, Constants.piston2Port2);
    	hooks = new Hooks(22);
    	
    	// Gyro init 
    	gyro = new Gyro(0);
    	
    	// Mechanum and Gyro booleans for Driver
    	useMechanum = true;
    	useGyro = false;
    	
    	// Controller init
    	xboxController = new XBoxController(1);
    	
    	// Encoder init
    	encoder = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
        encoder.setDistancePerPulse(1.0/360);
    	
    	// Serial init
    	//serialUSB = new SerialPort(9600,SerialPort.Port.kUSB);

    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// Vision init
    	//vision = new Vision();
    	
    	// Ultrasonic Sensor init
    	ultrasonic = new UltrasonicSensor(1);
    	
    	// Talon init
    	talon = new Talon(0);
    	
    	// PID Controller init
    	pidController = new PIDController(1, 0, 0, encoder, talon);
    	
    	// Lidar init
    	lidar = new Lidar(this);
    	
    	//NAVX init
    	try
    	{
	    	serialPortMXP = new SerialPort(57600, SerialPort.Port.kMXP);
	    	byte updateRateHz = 50;
	    	navx = new AHRS(serialPortMXP, updateRateHz);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("ERROR!: NAVX INIT" + "\n" + ex);
    	}
    	
    	compressor = new Compressor(0);
    	
    	// UI init
    	ui = new UI(this);
    	//smartUi = new SmartUI(this);
    	System.out.println("Robot Init successful.\n"
    					+ "RobotType: " + roboType.robotTypeName + "\n"
    					+ "Robot TeamNum: " + roboType.teamNum);
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
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
    	compressor.start();
    }
    public void teleopPeriodic()
    {
    	super.teleopPeriodic();
    	ui.update(this);
    	
    	lidar.getDistance();
    	
    	/*
    	 * Switches between Mechanum and Tank based on what wheels we are using.
    	 * It also switches between Cartesian and Polar Mechanum Drives based on 
    	 * whether or not we are using a gyro.
    	 */
    	driver.drive(xboxController, gyro, this);
    	hooks.cycle(xboxController);
    	ladder.cycle(xboxController);
    	
    	//lidar.update(this);
    }
    
    public void disabledInit()
    {
    	super.disabledInit();
    	compressor.stop();
    }
    public void disabledPeriodic()
    {
    	super.disabledPeriodic();
    	ui.update(this);
    }
    
    public void testInit()
	{
		super.testInit();
	}

	/**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	super.testPeriodic();
    }
    
}
