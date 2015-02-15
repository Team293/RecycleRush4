package autonomous;

import subsystems.DriveTrain;
import subsystems.Elevator;

public class BinTote extends Auto{
	/**
	 * Puts yellow bin without stacking the three bins in a stack.
	 */
	private static final double alignT = 0;
	private static final double liftT = 0;
	private static final double captureT = 0;
	private static final double turnT = 0;
	private static final double driveT = 0;
	
	public BinTote() {
		super();
	}

	public void init() {
		super.init();
	}

	public void run() {
		if (autoTimer.get() < alignT) {
			DriveTrain.tankDrive(0.5, 0.5);
		} else if (autoTimer.get() < liftT) {
			Elevator.setPresetPosition(3);
		} else if (autoTimer.get() < captureT) {
			DriveTrain.tankDrive(0.5, 0.5);
		} else if (autoTimer.get() < turnT) {
			DriveTrain.tankDrive(0, 0.5);
		} else if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(1, 1);
		}
	}

}
