package autonomous;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
	public static Timer autoTimer = new Timer();
	public static double initTLength = 0.15;
	public static double liftTLength = 1.23;
	public static double captureTLength = 0.4;
	public static double turnTLength = 2.5;
	public static double driveTLength = 2.3;
	public static double turn2TLength = 1.4;

	
	public void init() {
		autoTimer.start();
		
	}
	
	public void run() {
		
	}

}
