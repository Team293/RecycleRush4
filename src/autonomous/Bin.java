package autonomous;

import subsystems.DriveTrain;
import subsystems.Elevator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Bin extends Auto{
	private static double liftT = liftTLength;
	private static double turnT = liftT + turnTLength;
	private static double driveT = turnT + driveTLength;


	public Bin() {
		super();
	}
	
	public void run() {
		if (Elevator.isDown()) {
			zeroed = true;
		}
		if (!zeroed) {
			Elevator.updateManualPosition(false);
			Elevator.periodicPControl();
			autoTimer.reset();
		} else if (autoTimer.get() < liftT) {
			Elevator.setPresetPosition(3);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "lifting");
		} else if (autoTimer.get() < turnT) {
			DriveTrain.tankDrive(0, -turnSpeed);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning");
		} else if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(-driveSpeed, -driveSpeed);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "driving");
		} else {
			DriveTrain.stop();
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "stopping");
		}
	}
}