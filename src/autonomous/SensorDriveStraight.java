package autonomous;

import subsystems.DriveTrain;
import subsystems.Elevator;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SensorDriveStraight {
	static int  hi=1;
	static int state=0;
	public static Timer autoTimer = new Timer();
	public static int count;
	static double distance;
	public static void init(){
		autoTimer.start();	
	}
	
	public static void blah() {
		

		SmartDashboard.putNumber("state", state);
	distance=DriveTrain.getDistance();

	if(state==0){//I'm moving forward
		DriveTrain.enable();
		state=1;
	}
	
	if(state==1&&autoTimer.get()>.5){  //stop and pickup can and stuff only calls once
	DriveTrain.disable();
	Elevator.setPresetPosition(5);   
	state=2;
	autoTimer.reset();
	DriveTrain.resetEncoders();
	}
	if (state==2&&autoTimer.get()>.5){//if we are in state 2 and we gave it .5 seconds pull forward
		DriveTrain.enable();	
		state=3;
	}
	if (state==3&&distance>5){//start our turn after 5 rotations keep moving forward called continuously
		count=DriveTrain.turnleft();
		if (count==89){
			state=4;
			DriveTrain.resetEncoders();
		}
	}
	if (state==4&&distance<=50){//we're moving over the bump
		if (distance==50){
			Elevator.setPresetPosition(1);
			state=5;
		}
	}
	Elevator.periodicPControl();
	

	
	}	
}