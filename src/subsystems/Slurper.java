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
	private static final boolean fowardposition=false;//this will read the current position of the slurpers, and swithc their directions accordingly
	//this leaves the need for more functions with this position as well as make it easier to read and add functions to.

	private static boolean targetDirection = false;


	private static void move(double Speed) {//why would we need control of either side.  I think one speed should be fine
		if (lbLimit.get() && Speed == reverse) //this is fine then we can use one speed.
            //read comments on new foward position boolean
			Speed = stop;
			fowardposition=false;
		 else if (lfLimit.get() && Speed == forward) {
			Speed = stop;
			fowardposition=false;
		}
		if (rbLimit.get() && Speed == reverse) {
			Speed = stop;
			fowardposition=true;

		} else if (rfLimit.get() && rSpeed == forward) {
			Speed = stop;
			fowardposition=true;
		}
        if (fowardposition==true){
		lFinger.set(Speed);//set speed of slurpers
		rFinger.set(Speed);//set speed on slurpers.
        }
        if (fowardposition==false){
        lFinger.set(reverse);//set speed of slurpers
		rFinger.set(reverse);//set speed on slurpers.
        }
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
//can't we integrate this with move?  this could use move and be much easier to read.
     // On second thought, lets move this to OI, much cleaner.

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
