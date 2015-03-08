
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
		autonomousChooser.addObject("pickup can", new CanPickup());
		autonomousChooser.addObject("move forward", new RobotSet());
		autonomousChooser.addObject("bin", new Bin());
		autonomousChooser.addObject("bin & tote", new BinTote());
		autonomousChooser.addObject("bin, tote & turn", new BinToteTurn());

		SmartDashboard.putData("Which Autonomous?", autonomousChooser);
		SmartDashboard.putBoolean("Middle Auto", false);
	}

	public void autonomousInit() {
		selectedAuto = (Auto) autonomousChooser.getSelected();
		selectedAuto.init();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("time", Auto.autoTimer.get());
		selectedAuto.run();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopInit() {
	}
	
	public void teleopPeriodic() {
		OI.controlDriveTrain();
		OI.controlElevator();
		OI.controlArm();
		OI.win(true);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
