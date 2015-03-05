package autonomous;

import subsystems.DriveTrain;
import subsystems.Elevator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BinTurn extends Auto{
	private static double liftT = liftTLength;
	private static double turnT = liftT + turnTLength;
	private static double driveT = turnT + driveTLength;
	private static double turn2T = driveT + turn2TLength;
	
	public BinTurn() {
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
		} else if (autoTimer.get() < turnT) {
			DriveTrain.tankDrive(0, 0.7);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning");
		} else if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(1, 1);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "driving");
		} else if (autoTimer.get() < turn2T) {
			DriveTrain.tankDrive(-0.7, 0.7);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning2");
		} else {
			DriveTrain.stop();
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "stopping");
		}
	}

}
