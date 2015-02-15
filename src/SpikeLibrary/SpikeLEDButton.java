package SpikeLibrary;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class SpikeLEDButton extends SpikeButton{

	private int outputNumber;
	private Timer timer = new Timer();
	private double periodLength = 0.25; //length of flashes in seconds
	private boolean flashState = false;
	
	public SpikeLEDButton(Joystick joystick, int buttonNumber, int outputNumber) {
		super(joystick, buttonNumber);
		this.joystick = joystick;
		timer.start();

	}
	
	public void setOutput(boolean state) {
		joystick.setOutput(outputNumber, state);
	}
	
	public void flash() {
		if (timer.get() > periodLength) {
			timer.reset();
			flashState = !flashState;
			setOutput(flashState);
		}
	}

}
