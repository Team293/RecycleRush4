package org.usfirst.frc.team293.robot;

import SpikeLibrary.SpikeButton;
import SpikeLibrary.SpikeLEDButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.Arm;
import subsystems.DriveTrain;
import subsystems.Elevator;
import subsystems.PDP;


public class OI {
	//gamepad or custom Launchpad
	private static final Joystick leftJoystick = new Joystick(Ports.leftJoystick);
	private static final Joystick rightJoystick = new Joystick(Ports.rightJoystick); 
	private static final Joystick launchpad = new Joystick(Ports.launchpad);

	private static final SpikeLEDButton elevatorMinB = new SpikeLEDButton(launchpad, Ports.elevatorMinBInput, Ports.elevatorMinBOutput);
	private static final SpikeLEDButton elevator0B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator1B = new SpikeLEDButton(launchpad, Ports.elevator1BInput, Ports.elevator1BOutput);
	private static final SpikeLEDButton elevator2B = new SpikeLEDButton(launchpad, Ports.elevator2BInput, Ports.elevator2BOutput);
	private static final SpikeLEDButton elevator3B = new SpikeLEDButton(launchpad, Ports.elevator3BInput, Ports.elevator3BOutput);
	private static final SpikeLEDButton elevator4B = new SpikeLEDButton(launchpad, Ports.elevator4BInput, Ports.elevator4BOutput);
	private static final SpikeLEDButton elevator5B = new SpikeLEDButton(launchpad, Ports.elevator5BInput, Ports.elevator5BOutput);

	private static final SpikeButton softSwitch = new SpikeButton(launchpad, Ports.lever);
	private static final SpikeLEDButton elevatorDownB = new SpikeLEDButton(launchpad, Ports.elevatorDownB, Ports.elevatorDownBOutput);
	private static final SpikeLEDButton elevatorUpB = new SpikeLEDButton(launchpad, Ports.elevatorUpB, Ports.elevatorUpBOutput);

	private static final SpikeButton oneToteB = new SpikeButton(rightJoystick, Ports.trigger);
	private static final SpikeButton slowDriveB = new SpikeButton(leftJoystick, Ports.trigger);
	private static final SpikeButton disableArmB = new SpikeButton(leftJoystick, Ports.disableArm);


	public static void win(boolean isStarted) {
		if (isStarted) {
			//win
		} else {
			//win anyways
		}
	}

	public static void controlDriveTrain() {
		if (slowDriveB.isHeld()) {
			DriveTrain.slowDrive(-leftJoystick.getY(), -rightJoystick.getY());
		} else {
			DriveTrain.tankDrive(-leftJoystick.getY(), -rightJoystick.getY());
		}
	}

	public static void controlArm() {
		Arm.setPosition(-launchpad.getRawAxis(Ports.armA));
		if (disableArmB.isToggled()) {
			Arm.move(0);
		} else {
			Arm.periodicControl();
		}
	}

	public static void monitorElevatorB(SpikeLEDButton button, double position) {
		if (Elevator.getTargetPosition() == position) {
			if (Elevator.onTarget()) {
				button.setOutput(true);
			}  else {
				button.flash();
			}
		} else {
			button.setOutput(false);
		}
	}

	public static void controlElevator() {
		launchpad.setOutput(Ports.indicatorStrip, Elevator.isAligned());
		elevatorUpB.setOutput(elevatorUpB.isHeld());
		elevatorDownB.setOutput(elevatorDownB.isHeld());
		SmartDashboard.putBoolean("isAligned", Elevator.isAligned());
		if (softSwitch.isHeld()) {
			Elevator.setSoftMode(true);
		} else {
			Elevator.setSoftMode(false);
		}	
		if (elevatorUpB.isHeld() || elevatorDownB.isHeld()) {
			Elevator.setManualMode(true);
			if (elevatorUpB.isHeld()) {
				Elevator.updateManualPosition(true);
			} else if(elevatorDownB.isHeld()) {
				Elevator.updateManualPosition(false);
			}
		} else {
			if (oneToteB.isBumped()) {
				Elevator.toggleOneTote();
				Elevator.setManualMode(false);
			} else if (elevatorMinB.isBumped()) {
				Elevator.setPosition(-50000);
				Elevator.setManualMode(true);
			} else if (elevator0B.isBumped()) {
				Elevator.setPresetPosition(0);
				Elevator.setManualMode(false);
			} else if (elevator1B.isBumped()) {
				Elevator.setPresetPosition(1);
				Elevator.setManualMode(false);
			} else if (elevator2B.isBumped()) {
				Elevator.setPresetPosition(2);
				Elevator.setManualMode(false);
			} else if (elevator3B.isBumped()) {
				Elevator.setPresetPosition(3);
				Elevator.setManualMode(false);
			} else if (elevator4B.isBumped()) {
				Elevator.setPresetPosition(4);
				Elevator.setManualMode(false);
			} else if (elevator5B.isBumped()) {
				Elevator.setPresetPosition(5);
				Elevator.setManualMode(false);
			}
		}
		Elevator.periodicPControl();

		monitorElevatorB(elevatorMinB, 0);
		monitorElevatorB(elevator0B, Elevator.positions[0]);
		monitorElevatorB(elevator1B, Elevator.positions[1]);
		monitorElevatorB(elevator2B, Elevator.positions[2]);
		monitorElevatorB(elevator3B, Elevator.positions[3]);
		monitorElevatorB(elevator4B, Elevator.positions[4]);
		monitorElevatorB(elevator5B, Elevator.positions[5]);

	}

	public static void controlPDP() {
		PDP.monitor();
	}

}
