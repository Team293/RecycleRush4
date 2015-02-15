package subsystems;

public class Integration {
	private static int step = 0;
	public static void rightTote() {
		if (step == 0) {
			Arm.setPosition(0);
			Elevator.setPosition(0);
		} else if (step == 1) {
			Elevator.setPosition(0);
		} else if (step == 2) {
			Elevator.setPosition(0);
		} else if (step == 3) {
			Arm.setPosition(0);
		} else if (step == 4) {
			Elevator.setPosition(0);
		} else if (step == 5) {
			Arm.setPosition(0);
		}
		Elevator.periodicPControl();
		Arm.periodicControl();
		if (Elevator.onTarget() && Arm.onTarget()) {
			step += 1;
		}
	}

}
