package autonomous;

import subsystems.Arm;
import subsystems.DriveTrain;
import subsystems.Elevator;

public class SensorPickupCan {
public static int state=0;
public static Timer autoTimer = new Timer();
	public static void init(){
		autoTimer.start();		
	}
public static void blah(){
	if (state=0){
	Arm.setPosition(1200);
	state=1;
	}
	if (state=1&&autoTimer.get>.25){
	Elevator.setPresetPosition(4);
	state=2;
	}
	if (state=2&&autoTimer.get()>.5){
	DriveTrain.enable();
	state=3;
	}
	if (state=3&&autoTimer.get()>5){
	DriveTrain.disable();
	state=4;
	}
	if(state==4){
	Arm.setPosition(200);
	Elevator.setPresetPosition(2);
	}
	Elevator.periodicPControl();
	Arm.periodicControl();
}
}
