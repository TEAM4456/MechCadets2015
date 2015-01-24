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
import edu.wpi.first.wpilibj.Talon;
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
	CANTalon canTalon1, canTalon2, canTalon3, canTalon4;
	PIDController pidController;
	
	double pValue;
	boolean useGyro, useMechanum;
	
    public void robotInit()
    {
    	gyro = new Gyro(0);
    	useMechanum = true;
    	useGyro = false;
    	
    	// Encoder init
    	encoder = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
        encoder.setDistancePerPulse(1.0/360);
        
    	xboxController = new Joystick(1);
    	
    	// PID init
    	// pValue = -.5;
    	// pidController = new PIDController(pValue, 0, 0, encoder, testMotor);
    	
        // Driver init
    	driver = new Driver();
    	
    	// UI init
    	ui = new UI(this);
    	
    	accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
    	
    	limitSwitch = new DigitalInput(9);
    	
    	// Lidar init
    	lidar = new Lidar();
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
    	super.autonomousPeriodic();
    }
    
    //----------------
    //TELEOP
    //----------------
    public void teleopInit()
    {
    	super.teleopInit();
    	//pidController.enable();
    }
    
    public void teleopPeriodic()
    {
    	//pidController.setSetpoint(10);
    	
    	ui.update(this);
    	
    	driver.drive(xboxController, gyro, this);
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
