package subsystems;

import org.usfirst.frc.team293.robot.Ports;



import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	private static final double diameter = 10;

    private static final Encoder leftEncoder = new Encoder(Ports.leftDriveEncoder1, Ports.leftDriveEncoder2);
    private static final Encoder rightEncoder = new Encoder(Ports.rightDriveEncoder1, Ports.rightDriveEncoder2);
    
    private static final VictorSP leftMotor = new VictorSP(Ports.leftDrive);
    private static final VictorSP rightMotor = new VictorSP(Ports.rightDrive);
    
    private static final RobotDrive drive = new RobotDrive(leftMotor, rightMotor);

    //Underneath is all stuff for Straight drive
    private static Gyro gyro;
	private static PIDRobotDrive pidRobotDrive;	// this wraps RobotDrive
	private static PIDGyro pidGyro;	
	static PIDController autoDrivePID;
	static double direction=0;   
	static int status=1;
	static double leftencoder;
	static double rightencoder;
	static double averageencoder;
	static int count;
    public static void stop() {
    	drive.tankDrive(0,0);
    }
	public static void tankDrive(double leftSpeed, double rightSpeed) {
		drive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public static void squaredDrive(double leftValue, double rightValue) {
		leftValue = Math.signum(leftValue) * leftValue * leftValue;
		rightValue = Math.signum(rightValue) * rightValue * rightValue;
		tankDrive(leftValue, rightValue);
	}
	
	public static void skidControl(double leftInput, double rightInput) {
		
	}
	
	public static void arcadeDrive(double move, double rotate) {
		drive.arcadeDrive(move, rotate);
	}

	public static double convertToDistance(double count) {
		double rotations  = count/256;
		double distance = Math.PI*diameter*rotations;
		
		return distance;
	}
	
	public static double getDistance() {
		return convertToDistance((leftEncoder.get() + rightEncoder.get())/2);
	}
	public static void printEncoders(){
	SmartDashboard.putNumber("rightEncoder", -rightEncoder.get());
	SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
	}
	public static void printScaledEncoder(){
		SmartDashboard.putNumber("rightscaledEncoder", -rightEncoder.get()/256*3.14*diameter);
		SmartDashboard.putNumber("leftscaledEncoder", leftEncoder.get()/256*3.14*diameter);
	}
	
	private static class PIDRobotDrive implements PIDOutput {
		public void pidWrite(double output) {
			drive.drive(-0.2, output);	// use pid output to steer
		}
	}

	// alternatively, could also try feeding gyro into PIDController().
	private static class PIDGyro implements PIDSource {
		public double pidGet() {
			double angle = gyro.getAngle();	// angle could be > 360, or -ve
			angle %= 360;				// angle will be positive after modulo
			if (angle > 180)			// range is 0 to 360
				angle -= 360;			// range is now -180 to +180
			angle /= 180;				// range is now -1 to +1
			angle=direction+angle;
			return -angle;
		}
	}

	// change PID constants
	public void setPID(double p, double i, double d) {
		autoDrivePID.setPID(p, i, d);
	}

	public static void gyroInit() {
		gyro = new Gyro(0);             // Gyro on Analog Channel 1
		gyro.setSensitivity(0.0125); 	// ADW22307:  depends on gyro model

        drive.setExpiration(0.1);		// set timeout to 100ms
		drive.setSensitivity(1.0);		// set turn radius, needs experimentation

			// contains pidWrite()
		pidRobotDrive = new PIDRobotDrive();
		pidGyro = new PIDGyro();				// contains pidGet()

		// values for kP, kI, kD can be updated using setPID()
		autoDrivePID = new PIDController(0.35, 0.0, 0.0, pidGyro, pidRobotDrive);
		autoDrivePID.setContinuous(false);
		autoDrivePID.setInputRange(-1.0, +1.0);
		autoDrivePID.setOutputRange(-1.0, +1.0);
		autoDrivePID.setTolerance(1.0);			// 1%
		autoDrivePID.setSetpoint(0.0);
	}
	public static void enable(){
		if (status==1){
		autoDrivePID.enable();
		status=0;
		}
			
	}
	public static void disable(){
		if (status==0){
		autoDrivePID.disable();
		status=1;
		}	
	}
	public static int turnleft(){
		direction=direction+.005;	
		count=count+1;
		return count;
	}
	public static double getAvgDistance(){
		leftencoder =leftEncoder.get()/256*6*Math.PI;  //spread out wheel diameter 6 to make sense
		rightencoder=-rightEncoder.get()/256*6*Math.PI;
		averageencoder=leftencoder+rightencoder/2;
		return averageencoder;	
	}
	public static void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
}
