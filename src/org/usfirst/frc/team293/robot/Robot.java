
package org.usfirst.frc.team293.robot;

import autonomous.*;

import subsystems.DriveTrain;
import subsystems.Elevator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	SendableChooser autonomousChooser = new SendableChooser();
	Auto selectedAuto;

	public void robotInit() {
		autonomousChooser.addObject("nothing", new Nothing());
		autonomousChooser.addObject("bin & turn", new BinTurn());
		autonomousChooser.addObject("bin, tote & turn", new BinToteTurn());
		autonomousChooser.addObject("robot set", new RobotSet());

		SmartDashboard.putData("Which Autonomous?", autonomousChooser);
		//DriveTrain.gyroInit();
		//SensorTurnRight.init();
	}

	public void autonomousInit() {
		selectedAuto = (Auto) autonomousChooser.getSelected();
		Elevator.reset();
		
		selectedAuto.init();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("time", Auto.autoTimer.get());
		selectedAuto.run();
		//SensorTurnRight.blah();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopInit() {
		Elevator.reset();
	}
	
	public void teleopPeriodic() {
		OI.controlDriveTrain();
		OI.controlElevator();
		OI.controlArm();
		OI.controlPDP();
		/*DriveTrain.printEncoders();
		DriveTrain.printScaledEncoder();*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
