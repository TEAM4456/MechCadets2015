package org.usfirst.frc.team4456.robot;

/**
 * enum for all robot types. Makes it easier to switch from the 3 different robots we are using.
 * PRACTICE_BOT is for the practice 6000 robot
 * MAIN_BOT is for the main 4456 robot
 * BREADBOARD_BOT is for the 5000 breadboard
 * @author samega15
 *
 */
public enum RobotType
{
	PRACTICE_BOT(17, 18, 20, 19, 6000),
	MAIN_BOT(14, 21, 16, 12, 4456),
	BREADBOARD_BOT(11,10,15,22, 5000);
	
	//CAN ids for motors.
	public final int idRL, idFL, idRR, idFR;
	public final int teamNum;
	
	private RobotType(int idRL, int idFL, int idRR, int idFR, int teamNum)
	{
		this.idRL = idRL;
		this.idFL = idFL;
		this.idRR = idRR;
		this.idFR = idFR;
		this.teamNum = teamNum;
	}
}
