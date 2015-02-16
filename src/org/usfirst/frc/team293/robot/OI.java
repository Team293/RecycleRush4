package org.usfirst.frc.team293.robot;

import SpikeLibrary.SpikeButton;
import SpikeLibrary.SpikeLEDButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.Arm;
import subsystems.DriveTrain;
import subsystems.Elevator;
import subsystems.Integration;
import subsystems.PDP;


public class OI {
	//gamepad or custom Launchpad
	private static final Joystick leftJoystick = new Joystick(Ports.leftJoystick);
	private static final Joystick rightJoystick = new Joystick(Ports.rightJoystick); 
	private static final Joystick launchpad = new Joystick(Ports.launchpad);

	private static final SpikeLEDButton elevator0B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator1B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator2B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator3B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator4B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);
	private static final SpikeLEDButton elevator5B = new SpikeLEDButton(launchpad, Ports.elevator0BInput, Ports.elevator0BOutput);

	private static final SpikeButton softSwitch = new SpikeButton(launchpad, Ports.lever);
	private static final SpikeButton elevatorDownB = new SpikeButton(launchpad, Ports.elevatorDownB);
	private static final SpikeButton elevatorUpB = new SpikeButton(launchpad, Ports.elevatorUpB);
	private static final SpikeLEDButton rightBinB = new SpikeLEDButton(launchpad,Ports.arm, Ports.rightBin);

	private static final SpikeButton oneToteB = new SpikeButton(rightJoystick, Ports.trigger);

	private static boolean running=false;

	public static void controlDriveTrain() {
		DriveTrain.tankDrive(-leftJoystick.getY(), -rightJoystick.getY());
	}

	public static void controlArm() {
		if (rightBinB.isBumped()) {
			rightBinB.setOutput(true);
			Integration.rightTote();
			 running=Integration.rightTote();
		} else if (running==false){
			rightBinB.setOutput(false);
			Arm.setPosition(-launchpad.getRawAxis(Ports.armA));
			Arm.periodicControl();
		}
	}

	public static void monitorElevatorB(SpikeLEDButton button, int positionIndex) {
		if (Elevator.getTargetPosition() == Elevator.positions[positionIndex]) {
			if (Elevator.onTarget()) {
				button.setOutput(true);
			} else {
				button.flash();
			}
		} else {
			button.setOutput(false);
		}
	}
	
	public static void controlElevator() {
		
		launchpad.setOutput(Ports.yellowIndicator, Elevator.isAligned());
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
		
		monitorElevatorB(elevator0B, 0);
		monitorElevatorB(elevator1B, 1);
		monitorElevatorB(elevator2B, 2);
		monitorElevatorB(elevator3B, 3);
		monitorElevatorB(elevator4B, 4);
		monitorElevatorB(elevator5B, 5);
		
	}

	public static void controlPDP() {
		PDP.monitor();
	}

}
