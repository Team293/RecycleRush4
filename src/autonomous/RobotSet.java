package autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.DriveTrain;
import subsystems.Elevator;

public class RobotSet extends Auto{
	private static double driveT = 1.5;

	public RobotSet() {
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
		} else if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(driveSpeed, driveSpeed);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "driving");
		}
	}
}
