package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team4456.robot.util.*;

/**
 * class for the winch on the ladder device that picks up the trash can.
 * @author MechCadets
 *
 */
public class Ladder
{
	private CANTalon talon;
	private boolean dpadDownPress = false, dpadUpPress = false;
	private int currentTargetIndex;
	
	private DoubleSolenoid piston1, piston2;
	
	/**
	 * Constructor for WinchLadder
	 * this will construct a new talon motor object with the appropriate pid settings
	 * @param idTalon talon motor id
	 */
	public Ladder(int idTalon, int id1_1, int id1_2, int id2_1, int id2_2)
	{
		talon = new CANTalon(idTalon);
		talon.changeControlMode(ControlMode.Position);
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.setPID(1.0, 0.000001, 0);
		talon.set(talon.get()); //talon1 will not move
		
		piston1 = new DoubleSolenoid(id1_1, id1_2);
		piston2 = new DoubleSolenoid(id2_1, id2_2);
	}
	
	/**
	 * Periodic cycle function for Ladder
	 * @param controller xboxController
	 */
	public void cycle(XBoxController controller)
	{
		//DPad_Down lowerLadder
		if(controller.getDPadDown() && !dpadDownPress)
		{
			dpadDownPress = true;
			this.lowerLadder();
		}
		else if(!controller.getA() && dpadDownPress)
		{
			dpadDownPress = false;
		}
		
		//DPad_Up raiseLadder
		if(controller.getDPadUp() && !dpadUpPress)
		{
			dpadUpPress = true;
			this.raiseLadder();
		}
		else if(!controller.getB() && dpadUpPress)
		{
			dpadUpPress = false;
		}
		
		//dpadLeft nudgeDown
		if(controller.getDPadLeft())
		{
			double newSetPoint = Util.max(talon.getSetpoint() - (Constants.LADDER_NUDGE_FACTOR),0.0);
			talon.set(newSetPoint);
		}
		
		//dpadRight nudgeUp
		if(controller.getDPadRight())
		{
			double newSetPoint = Util.min(talon.getSetpoint() + (Constants.LADDER_NUDGE_FACTOR),
										  Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1]);
			talon.set(newSetPoint);
		}
		
		// ButtonA close
		if(controller.getA())
		{
			close();
		}
		// ButtonB open
		if(controller.getB())
		{
			open();
		}
	}

// ----------------------------------------------------------------------
//AUX FUNCTIONS ---------------------------------------------------------
// ----------------------------------------------------------------------
	
	/*
	 * Extend the pistons to close
	 */
	private void close()
	{
		piston1.set(Value.kForward);
		piston2.set(Value.kForward);
	}
	private void open()
	{
		piston1.set(Value.kReverse);
		piston2.set(Value.kReverse);
	}
	
	private void raiseLadderMax()
	{
		talon.set(Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1]);
		this.currentTargetIndex = Constants.WINCH_LADDER_POSITIONS.length-1;
	}
	
	private void raiseLadder()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex >= Constants.WINCH_LADDER_POSITIONS.length-1)
		{
			targetIndex = Constants.WINCH_LADDER_POSITIONS.length - 1;
		}
		else
		{
			targetIndex = closestIndex + 1;
		}
		talon.set(Constants.WINCH_LADDER_POSITIONS[targetIndex]);
		this.currentTargetIndex = targetIndex;
	}
	
	private void lowerLadder()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex <= 0)
		{
			targetIndex = 0;
		}
		else
		{
			targetIndex = closestIndex-1;
		}
		talon.set(Constants.WINCH_LADDER_POSITIONS[targetIndex]);
		this.currentTargetIndex = targetIndex;
	}
	
	private void lowerWinchMin()
	{
		talon.set(Constants.WINCH_LADDER_POSITIONS[0]);
		this.currentTargetIndex = 0;
	}
	
	private int findClosestPosition()
	{
		double currentPos = talon.get();
		double closestDistance = 0;
		int closestIndex = 0;
		double highestPos = Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1];
		double lowestPos = Constants.WINCH_LADDER_POSITIONS[0];
		if(currentPos > highestPos)
		{
			return Constants.WINCH_LADDER_POSITIONS.length-1;
		}
		else if(currentPos < lowestPos)
		{
			return 0;
		}
		else
		{
			for(int i = 0; i < Constants.WINCH_LADDER_POSITIONS.length; i++)
			{
				double distance = Math.abs(currentPos - Constants.WINCH_LADDER_POSITIONS[i]);
				if(distance < closestDistance || i == 0)
				{
					closestDistance = distance;
					closestIndex = i;
				}
			}
		}
		return closestIndex;
	}
}
