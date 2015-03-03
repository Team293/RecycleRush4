package subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RightTote {
	private static int step = 0;
	public static boolean rightTote() {
		
		if (step == 0) {
			// move arm and elevator to correct height
			Arm.setPosition(0);
			SmartDashboard.putNumber("step", 0);
			Elevator.setPosition(0);
		} else if (step == 1) {
			// move elevator up
			Elevator.setPosition(0);
		} else if (step == 2) {
			// move elevator back down
			Elevator.setPosition(0);
		} else if (step == 3) {
			// move arm down a bit
			Arm.setPosition(0);
		} else if (step == 4) {
			// move elevator up
			Elevator.setPosition(0);
		} else if (step == 5) {
			// move arm back to some position
			Arm.setPosition(0);	
		}
		
		Elevator.periodicPControl();
		Arm.periodicControl();
		if (Elevator.onTarget() && Arm.onTarget()) {
			step += 1;
		}
		return true;
		
	}


}
