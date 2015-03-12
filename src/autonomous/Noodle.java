package autonomous;

import subsystems.DriveTrain;

public class Noodle extends Auto{
	private static double turnT = .6;
	
	public Noodle() {
		super();
	}
	
	public void run() {
		zero();
		if (zeroed && autoTimer.get() < turnT) {
			DriveTrain.tankDrive(-0.3, -0.7);
		}
	}
}
