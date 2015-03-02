package autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.DriveTrain;
import subsystems.Elevator;

public class BinToteTurn extends Auto{
	/**
	 * Puts yellow bin without stacking the three bins in a stack.
	 */
	private static double liftT = liftTLength;
	private static double captureT = liftT + captureTLength;
	private static double turnT = captureT + turnTLength;
	private static double driveT = turnT + driveTLength;
	private static double turn2T = driveT + turn2TLength;
	
	public BinToteTurn() {
		super();
	}

	public void init() {
		super.init();
	}

	public void run() {
		if (autoTimer.get() < liftT) {
			Elevator.setPresetPosition(3);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "lifting");
		} else if (autoTimer.get() < captureT) {
			DriveTrain.tankDrive(0.8, 0.8);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "capturing");
		} else if (autoTimer.get() < turnT) {
			DriveTrain.tankDrive(0, 0.7);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning");
		} else if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(1, 1);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "driving");
		} else if (autoTimer.get() < turn2T) {
			DriveTrain.tankDrive(0.7, -0.7);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning2");
		} else {
			DriveTrain.stop();
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "stopping");
		}
	}

}
