package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import SpikeLibrary.SpikeLimit;
import SpikeLibrary.SpikeMath;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	private static final VictorSP elevator = new VictorSP(Ports.elevator);
	private static final Encoder encoder = new Encoder(Ports.elevatorEncoder1, Ports.elevatorEncoder2);
	private static final SpikeLimit topLimit = new SpikeLimit(Ports.elevatorTopLimit);
	private static final SpikeLimit bottomLimit = new SpikeLimit(Ports.elevatorBottomLimit);
	private static final SpikeLimit toteLimit = new SpikeLimit(Ports.toteSensor);
	private static boolean manualMode = true;
	private static boolean softMode = false;
	private static double targetPosition = 0;
	private static double finalTargetPosition = 0;
	private static final double PICKUP = 0.5;
	private static final double TRAVEL = 4.75;
	private static final double ONETOTE = 15.75;
	private static final double CANONTOTE = 20;
	private static final double TWOTOTE = 28;
	private static final double CANONTWOTOTE = 32;
	public static double[] positions = new double[] {PICKUP, TRAVEL, ONETOTE, CANONTOTE, TWOTOTE, CANONTWOTOTE};
	private static double kP = 1.19;
	private static final double encoderScale = 512; //counts per rotation
	private static final double circumference = 7.56; //of belt gear

	public static void reset() {
		encoder.reset();
		encoder.setDistancePerPulse(0.01477);
		targetPosition = 0;
	}

	public static void move(double speed) {
		//stops from moving through limits
		if (topLimit.isHeld()) {
			speed = SpikeMath.cap(speed, -1, 0);
		} else if (bottomLimit.isHeld()) {
			speed = SpikeMath.cap(speed, 0, 1);
			encoder.reset();
			if (targetPosition < 0) {
				targetPosition = 0;
			}
		}
		elevator.set(speed);
	}
	
	public static void setPosition(double position) {
		targetPosition = position;
	}
	
	public static boolean isAligned() {
		if (toteLimit.isHeld()) {
			return true;
		}
		return false;
	}

	public static void setManualMode(boolean newMode) {
		//sets whether is controlled manually or through preset positions
		manualMode = newMode;
	}

	public static boolean getManualMode() {
		//returns whether is controlled manually or through preset positions
		return manualMode;
	}

	public static void setSoftMode(boolean mode) {
		softMode = mode;
	}

	public static void hardSetPresetPosition(int positionInput) {
		//set the target position to a preset position
		finalTargetPosition = positions[positionInput];
		targetPosition = finalTargetPosition;
	}

	public static void softSetPresetPosition(int positionInput) {
		finalTargetPosition = positions[positionInput];
	}

	public static void setPresetPosition(int positionInput) {
		if (softMode) {
			softSetPresetPosition(positionInput);
		} else {
			hardSetPresetPosition(positionInput);
		}
	}
	
	public static double getTargetPosition() {
		return targetPosition;
	}

	public static void updateManualPosition(boolean direction) {
		//change the target position manually
		targetPosition += (direction ? 0.25:-0.2);
	}

	public static void toggleOneTote() {
		targetPosition = ((targetPosition == PICKUP) ? ONETOTE:PICKUP);
	}

	public static double getInches() {
		double rotations = encoder.get()/encoderScale;
		double inches = rotations * circumference;
		return -inches;
	}
	
	public static boolean onTarget() {
		if (Math.abs(targetPosition - getInches()) <= 0.2) {
			return true;
		}
		return false;
	}

	public static void periodicPControl() {
		//go to the target position
		double currentPosition = getInches();
		double error = finalTargetPosition - targetPosition;
		if (error >= 0.125) {
			updateManualPosition(true);
		} else if (error <= -0.125) {
			updateManualPosition(false);
		}
		SmartDashboard.putNumber("currentPosition", currentPosition);
		SmartDashboard.putNumber("targetPosition", targetPosition);
		double rawError = targetPosition - currentPosition;
		double output = rawError * kP;
		//limiting output to elevator motor speed
		//if (output < -.5) {
			//output = -.5;
		//}
		move(output);
	}
}
