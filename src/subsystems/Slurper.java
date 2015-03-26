package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Slurper {
	//l = left, r = right, b = back, f = front

	private static final Talon lFinger = new Talon(Ports.lBelt);
	private static final Talon rFinger = new Talon(Ports.rBelt);

	public static final DigitalInput lbLimit = new DigitalInput(Ports.lbLimit);
	public static final DigitalInput rbLimit = new DigitalInput(Ports.rbLimit);
	private static final DigitalInput lfLimit = new DigitalInput(Ports.lfLimit);
	private static final DigitalInput rfLimit = new DigitalInput(Ports.rfLimit);
	private static final DigitalInput lOpticalLimit = new DigitalInput(Ports.lOpticalLimit);
	private static final DigitalInput rOpticalLimit = new DigitalInput(Ports.rOpticalLimit);
	private static final DigitalInput lToteLimit = new DigitalInput(Ports.lToteLimit);
	private static final DigitalInput rToteLimit = new DigitalInput(Ports.rToteLimit);

	private static final double forward = 0.15;
	private static final double stop = 0;
	private static final double reverse = -forward;
	private static boolean targetDirection = true;

	public static void move(double lSpeed, double rSpeed) {
		//prevents from going through limits
		if (lbLimit.get() && lSpeed == reverse) {
			lSpeed = stop;
		} else if (lfLimit.get() && lSpeed == forward) {
			lSpeed = stop;
		}

		if (rbLimit.get() && rSpeed == reverse) {
			rSpeed = stop;
		} else if (rfLimit.get() && rSpeed == forward) {
			rSpeed = stop;
		}

		lFinger.set(-lSpeed);
		rFinger.set(rSpeed);
	}

	public static boolean isBack() {
		if (lbLimit.get() && rbLimit.get()) {
			return true;
		}
		return false;
	}
	
	public static boolean isHalfIn() {
		if (lOpticalLimit.get() && rOpticalLimit.get()) {
			return true;
		}
		return false;
	}

	public static boolean isForward() {
		if (lfLimit.get() && rfLimit.get()) {
			return true;
		}
		return false;

	}

	public static void manualMove(boolean toggleDirection) {
		if (toggleDirection) {
			//changes target direction if button is hit
			targetDirection = !targetDirection;
		}
		//moves in target direction, forward is true
		if (targetDirection) {
			move(forward, forward);
		} else {
			move(reverse, reverse);
		}
	}
	
	public static void autoMove() {
		//moves automatically based on where a tote is sensed
		
		//updates target direction based on current position in case switched back into manual mode
		if (isBack()) {
			targetDirection = false;
		} else if (isForward()) {
			targetDirection = true;
		}

		//default is don't move
		double lSpeed = stop;
		double rSpeed = stop;

		//if releasing a stack, go forwards
		if (Elevator.getTargetPosition() < Elevator.positions[1]) {
			lSpeed = forward;
			rSpeed = forward;
		} else {
			//if a tote is partway in, move backwards
			if (isHalfIn() && !lbLimit.get()) {
				lSpeed = reverse;
			} else if (!lbLimit.get() && !rbLimit.get()) {
				//if there is no tote, reset to forwards
				lSpeed = forward;
			}

			//same stuff for the right side
			if (isHalfIn() && !rbLimit.get()) {
				rSpeed = reverse;
			} else if (!lbLimit.get() && !rbLimit.get()) {
				rSpeed = forward;
			}
		}
		//actually set the speeds
		move(lSpeed, rSpeed);
	}
}