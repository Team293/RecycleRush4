package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import SpikeLibrary.SpikeMath;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;

public class Slurper {
	//l = left, r = right, b = back, f = front

	private static final Talon lFinger = new Victor(Ports.lBelt);
	private static final Talon rFinger = new Victor(Ports.rBelt);

	private static final DigitalInput lbScrewLimit = new DigitalInput(Ports.lbScrewLimit);
	private static final DigitalInput rbScrewLimit = new DigitalInput(Ports.rbScrewLimit);
	private static final DigitalInput lfScrewLimit = new DigitalInput(Ports.lfScrewLimit);
	private static final DigitalInput rfScrewLimit = new DigitalInput(Ports.rfScrewLimit);

	private static final DigitalInput lbToteLimit = new DigitalInput(Ports.lbToteLimit);
	private static final DigitalInput rbToteLimit = new DigitalInput(Ports.rbToteLimit);
	private static final DigitalInput lfToteLimit = new DigitalInput(Ports.lfToteLimit);
	private static final DigitalInput rfToteLimit = new DigitalInput(Ports.rfToteLimit);

	private static void move(double lSpeed, double rSpeed) {
		if (lbScrewLimit.get()) {
			lSpeed = SpikeMath.cap(lSpeed, 0, 1);
		} else if (lfScrewLimit.get()) {
			lSpeed = SpikeMath.cap(lSpeed, -1, 0);
		}

		if (rbScrewLimit.get()) {
			rSpeed = SpikeMath.cap(rSpeed, 0, 1);
		} else if (rfScrewLimit.get()) {
			rSpeed = SpikeMath.cap(rSpeed, -1, 0);
		}

		lFinger.set(lSpeed);
		rFinger.set(rSpeed);
	}

	public static void manualMove(double speed) {
		move(speed, speed);
	}

	private static boolean toteHalfIn() {
		if (lfToteLimit.get() && rfToteLimit.get()) {
			return true;
		}
		return false;
	}

	public static void autoMove() {
		double lSpeed = 0;
		double rSpeed = 0;

		if (Elevator.getTargetPosition() < Elevator.positions[1]) {
			lSpeed = 0.4;
			rSpeed = 0.4;
		} else {
			if (toteHalfIn() && !lbToteLimit.get()) {
				lSpeed = -0.4;
			} else if (!lbToteLimit.get()) {
				lSpeed = 0.4;
			}

			if (toteHalfIn() && !rbToteLimit.get()) {
				rSpeed = -0.4;
			} else if (!rbToteLimit.get()) {
				rSpeed = 0.4;
			}
		}
		move(lSpeed, rSpeed);
	}
}
