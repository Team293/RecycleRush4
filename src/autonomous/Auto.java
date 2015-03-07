package autonomous;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
	public static Timer autoTimer = new Timer();
	public static double liftTLength = 1.23;
	public static double captureTLength = 0.4;
	public static double turnTLength = 2.5;
	public static double driveTLength = 2.3;
	public static double turn2TLength = 1.2;
	//hello, from steve whos totally a programmer too
	public static double captureSpeed = 0.5;
	public static double turnSpeed = 0.55;
	public static double driveSpeed = 0.7;
	public static double turn2Speed = 0.7;
	
	public static boolean zeroed = false;

	
	public void init() {
		autoTimer.start();
		
	}
	
	public void run() {
		
	}

}
