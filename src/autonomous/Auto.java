package autonomous;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
	public static Timer autoTimer = new Timer();
	public static double driveToAutozoneTime = 4;
	
	public void init() {
		autoTimer.start();
	}
	
	public void run() {
		
	}

}
