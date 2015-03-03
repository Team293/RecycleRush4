package autonomous;

import subsystems.DriveTrain;

public class SensorDriveStraight {
	static double distance;
	public static void blah(){
	distance=DriveTrain.getDistance();
	DriveTrain.enable();
	if(distance>50){
		DriveTrain.disable();
	}
	}
}
