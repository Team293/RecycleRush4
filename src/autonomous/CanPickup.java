package autonomous;

import subsystems.DriveTrain;
import subsystems.Elevator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CanPickup extends Auto{
	private static double liftT = liftTLength;

	public CanPickup() {
		super();
	}

	public void init() {
		super.init();
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
		} else {
			DriveTrain.stop();
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "stopping");
		}
	}
}