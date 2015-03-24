package org.usfirst.frc.team4456.robot;

import java.util.Date;

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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	//Change the current robot used HERE
	RobotType roboType = RobotType.PRACTICE_BOT;
	
	XBoxController xboxController;
	
	Driver driver;
	
	boolean limitModeEnabled = true;
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
	public double speedFactor;
	boolean useGyro, useMechanum;
	
	Command autoCommand1, autoCommand2;
	SendableChooser autoChooser;

	boolean useAutoChooser = false;
	
    public void robotInit()
    {	
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
    	
    	// Driver init
    	speedFactor = 1;
    	driver = new Driver(roboType, navx.getYaw());
    	
    	compressor = new Compressor(0);
    	
    	//Autonomous Command Code init
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Autonomous1", new AutonomousCommand1(this));
    	autoChooser.addObject("Autonomous2", new AutonomousCommand2(this));
    	
    	// UI init
    	ui = new UI(this);
    	//smartUi = new SmartUI(this);
    	
    	//print startup message
    	System.out.println("\n---Robot Init successful.---\n\n"
    					+ "RobotType:\t\t" + roboType.robotTypeName + "\n"
    					+ "Robot TeamNum:\t" + roboType.teamNum + "\n"
    					+ "DATE:\t\t\t" + (new Date()).toString() + "\n");
    }
    
    public void autonomousInit()
    {
    	super.autonomousInit();
		
    	if(useAutoChooser)
    	{
	    	autoCommand1 = (Command) autoChooser.getSelected();
	    	autoCommand1.start();
    	}
    	else
    	{
    		int hookPositionsLength = Constants.HOOK_LOADER_POSITIONS.length;
    		
    		Timer timer = new Timer();
    		
    		timer.start();
    		System.out.println("Running AutoCommand2");
    		hooks.setIndex(hookPositionsLength - 2);
    		Timer.delay(.7);
    		
    		
    		while(!timer.hasPeriodPassed(2.9))
    		{
    			driver.driveRawPolar(.4, 180, 0);
    		}
    		
    		Timer.delay(.2);
    		hooks.setIndex(hookPositionsLength - 1);
    	}
    	
    }
    
    /**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		
		super.autonomousPeriodic();
		
		if(useAutoChooser)
		{
			Scheduler.getInstance().run();
			ui.update(this);
		}
		
		
		
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
    	driver.drive(xboxController, (float)gyro.getAngle(), this);
    	hooks.cycle(xboxController, this);
    	ladder.cycle(xboxController, this);
    	
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
