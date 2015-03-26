package subsystems;

import org.usfirst.frc.team293.robot.Ports;

import SpikeLibrary.SpikeMath;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Slurper {
	//l = left, r = right, b = back, f = front
        //CAN WE PLZ COMMENT LOL
	private static final Talon lFinger = new Talon(Ports.lBelt);
	private static final Talon rFinger = new Talon(Ports.rBelt);

	private static final DigitalInput lbLimit = new DigitalInput(Ports.lbLimit);
	private static final DigitalInput rbLimit = new DigitalInput(Ports.rbLimit);
	private static final DigitalInput lfLimit = new DigitalInput(Ports.lfLimit);
	private static final DigitalInput rfLimit = new DigitalInput(Ports.rfLimit);
	private static final DigitalInput lOpticalLimit = new DigitalInput(Ports.lOpticalLimit);
	private static final DigitalInput rOpticalLimit = new DigitalInput(Ports.rOpticalLimit);

	private static final double speed = 1;
	private static final double forward = speed;
	private static final double stop = 0;
	private static final double reverse = -speed;

	private static boolean targetDirection = false;


	private static void move(double Speed) {//why would we need control of either side.  I think one speed should be fine
		if (lbLimit.get() && Speed == reverse) //this is fine then we can use one speed.
			Speed = stop;
		 else if (lfLimit.get() && Speed == forward) {
			Speed = stop;
		}

		if (rbLimit.get() && Speed == reverse) {
			Speed = stop;
		} else if (rfLimit.get() && rSpeed == forward) {
			Speed = stop;
		}

		lFinger.set(Speed);//set speed of slurpers
		rFinger.set(Speed);//set speed on slurpers.
	}

	public static boolean isBack() {//a listener
		if (lbLimit.get() && rbLimit.get()) {
			return true;
		}
		return false;
	}

	public static boolean isForward() {//a listener
		if (lfLimit.get() && rfLimit.get()) {
			return true;
		}
		return false;

	}

	/*public static void manualMove(boolean toggleDirection) {//can't we integrate this with move?  this could use move and be much easier to read.
		if (toggleDirection) {              // On second thought, lets move this to OI, much cleaner.
			targetDirection = !targetDirection;
		}
		if (targetDirection) {
			move(forward, forward);
		} else {
			move(reverse, reverse);
		}
	}*/

	/*public static void autoMove() {
//we aren't getting half in limit....we're only getting the limits.  We should really focus on manual, if that's what Ben's gonna use/

		if (isBack()) {
			targetDirection = false;
		} else if (isForward()) {
			targetDirection = true;
		}

		double lSpeed = stop;
		double rSpeed = stop;

		if (Elevator.getTargetPosition() < Elevator.positions[1]) {
			lSpeed = forward;
			rSpeed = forward;
		} else {
			if (isHalfIn() && !lbLimit.get()) {
				lSpeed = reverse;
			} else if (!lbLimit.get()) {
				lSpeed = forward;
			}

			if (isHalfIn() && !rbLimit.get()) {
				rSpeed = reverse;
			} else if (!rbLimit.get()) {
				rSpeed = forward;
			}
		}
		move(lSpeed, rSpeed);
	}*/
}
