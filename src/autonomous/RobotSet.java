package autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.*;

public class RobotSet extends Auto {
	
	private static double driveT = driveTLength;
	private static double turn2T = driveT + turn2TLength;
	
	public RobotSet() {
		super();
	}
	
	public void init() {
		super.init();
	}
	
	public void run() {
		if (autoTimer.get() < driveT) {
			DriveTrain.tankDrive(1, 1);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "driving");
		} else if (autoTimer.get() < turn2T) {
			DriveTrain.tankDrive(-0.7, 0.7);
			Elevator.periodicPControl();
			SmartDashboard.putString("mode", "turning2");
		}
	}
}
