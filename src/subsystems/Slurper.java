package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import SpikeLibrary.SpikeMath;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class Slurper {
	//l = left, r = right, b = back, f = front

	private static final Relay lFinger = new Relay(Ports.lBelt);
	private static final Relay rFinger = new Relay(Ports.rBelt);

	private static final DigitalInput lbScrewLimit = new DigitalInput(Ports.lbScrewLimit);
	private static final DigitalInput rbScrewLimit = new DigitalInput(Ports.rbScrewLimit);
	private static final DigitalInput lfScrewLimit = new DigitalInput(Ports.lfScrewLimit);
	private static final DigitalInput rfScrewLimit = new DigitalInput(Ports.rfScrewLimit);

	private static final DigitalInput lbToteLimit = new DigitalInput(Ports.lbToteLimit);
	private static final DigitalInput rbToteLimit = new DigitalInput(Ports.rbToteLimit);
	private static final DigitalInput lfToteLimit = new DigitalInput(Ports.lfToteLimit);
	private static final DigitalInput rfToteLimit = new DigitalInput(Ports.rfToteLimit);

	private static void move(Relay.Value lSpeed, Relay.Value rSpeed) {
		if (lbScrewLimit.get() && lSpeed == Relay.Value.kReverse) {
			lSpeed = Relay.Value.kOff;
		} else if (lfScrewLimit.get() && lSpeed == Relay.Value.kForward) {
			lSpeed = Relay.Value.kOff;
		}

		if (rbScrewLimit.get() && rSpeed == Relay.Value.kReverse) {
			rSpeed = Relay.Value.kOff;
		} else if (rfScrewLimit.get() && rSpeed == Relay.Value.kForward) {
			rSpeed = Relay.Value.kOff;
		}
		rFinger.set(Relay.Value.kOff);

		lFinger.set(lSpeed);
		rFinger.set(rSpeed);
	}

	public static void manualMove(Relay.Value speed) {
		move(speed, speed);
	}

	private static boolean toteHalfIn() {
		if (lfToteLimit.get() && rfToteLimit.get()) {
			return true;
		}
		return false;
	}

	public static void autoMove() {
		Relay.Value lSpeed = Relay.Value.kOff;
		Relay.Value rSpeed = Relay.Value.kOff;

		if (Elevator.getTargetPosition() < Elevator.positions[1]) {
			lSpeed = Relay.Value.kForward;
			rSpeed = Relay.Value.kForward;
		} else {
			if (toteHalfIn() && !lbToteLimit.get()) {
				lSpeed = Relay.Value.kReverse;
			} else if (!lbToteLimit.get()) {
				lSpeed = Relay.Value.kReverse;
			}

			if (toteHalfIn() && !rbToteLimit.get()) {
				rSpeed = Relay.Value.kReverse;
			} else if (!rbToteLimit.get()) {
				rSpeed = Relay.Value.kForward;
			}
		}
		move(lSpeed, rSpeed);
	}
}
