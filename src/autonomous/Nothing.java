package autonomous;

import subsystems.Elevator;

public class Nothing extends Auto{
	public void run() {
		if (Elevator.isDown()) {
			zeroed = true;
		}
		if (!zeroed) {
			Elevator.updateManualPosition(false);
			Elevator.periodicPControl();
			autoTimer.reset();
		}
	}
}
