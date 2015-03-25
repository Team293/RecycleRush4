package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import SpikeLibrary.SpikeMath;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Slurper {
	//l = left, r = right, b = back, f = front

	private static final Talon lFinger = new Talon(Ports.lBelt);
	private static final Talon rFinger = new Talon(Ports.rBelt);

	private static final DigitalInput lbScrewLimit = new DigitalInput(Ports.lbScrewLimit);
	private static final DigitalInput rbScrewLimit = new DigitalInput(Ports.rbScrewLimit);
	private static final DigitalInput lfScrewLimit = new DigitalInput(Ports.lfScrewLimit);
	private static final DigitalInput rfScrewLimit = new DigitalInput(Ports.rfScrewLimit);

	public static final DigitalInput lbToteLimit = new DigitalInput(Ports.lbToteLimit);
	public static final DigitalInput rbToteLimit = new DigitalInput(Ports.rbToteLimit);
	public static final int forward=1;
	public static final int reverse=-1;

	private static void move() {
		if (lbScrewLimit.get() && lSpeed == lFinger.set(forward)) {
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

	public static boolean isBack() {
		if ((lbScrewLimit.get() && rbScrewLimit.get()) || (lbToteLimit.get() && lbToteLimit.get())) {
			return true;
		}
		return false;
	}

	public static boolean isForward() {
		if (lfScrewLimit.get() && rfScrewLimit.get()) {
			return true;
		}
		return false;

	}

	private static boolean targetDirection = false;

	public static void manualMove(boolean toggleDirection) {
		if (toggleDirection) {
			targetDirection = !targetDirection;
		}
		if (targetDirection) {
			move(lfinger, Relay.Value.kForward);
		} else {
			move(Relay.Value.kReverse, Relay.Value.kReverse);
		}

	}
}
