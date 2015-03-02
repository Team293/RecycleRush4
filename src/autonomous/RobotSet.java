package autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.*;

public class RobotSet extends Auto {
	
	private static double driveT = driveTLength;
	
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
		}
	}
}
