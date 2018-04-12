package org.discobots.lib.autonomous.commands;

import org.discobots.lib.autonomous.pid.EncoderPID;
import org.discobots.lib.autonomous.pid.GyroPID;
import org.discobots.lib.autonomous.pid.PIDDummyOutput;
import org.discobots.lib.autonomous.pid.PIDSourceAverageEncoder;
import org.discobots.lib.autonomous.pid.PIDSourceGyro;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class ArcadeGyroEncoderDrive extends Command implements EncoderPID, GyroPID {

	private DifferentialDrive drive;
	private Encoder left;
	private Encoder right;

	private double threshold;
	private double turningThreshold;
	private double encoderSetpoint;

	private double distanceEncoderError;
	private double turningEncoderError;

	private PIDDummyOutput distanceEncoderPIDOutput;
	private PIDController distanceEncoderPID;
	private PIDDummyOutput turningGyroPIDOutput;
	private PIDController turningGyroPID;
	private PIDSourceAverageEncoder avgEncoderPIDSource;
	private PIDSourceGyro turningGyroPIDSource;

	/**
	 * 
	 * @param encoderSetpoint
	 *            Distance setpoint in inches
	 * @param threshold
	 *            Distance error threshold
	 * @param kP
	 *            Distance proportional value
	 * @param kI
	 *            Distance integral value
	 * @param kD
	 *            Distance derivative value
	 * @param turningThreshold
	 *            Turning error threshold
	 * @param tP
	 *            Turning proportional value
	 * @param tI
	 *            Turning integral value
	 * @param tD
	 *            Turning derivative value
	 */
	public ArcadeGyroEncoderDrive(double encoderSetpoint, double threshold, double kP, double kI, double kD,
			double turningThreshold, double tP, double tI, double tD) {
		System.out.println("GyroEncoderDriveDistance Starting");

		drive = getDrivetrain();
		left = getLeftEncoder();
		right = getRightEncoder();

		this.encoderSetpoint = encoderSetpoint;
		this.threshold = threshold;
		this.turningThreshold = turningThreshold;

		distanceEncoderPIDOutput = new PIDDummyOutput();
		turningGyroPIDOutput = new PIDDummyOutput();

		avgEncoderPIDSource = new PIDSourceAverageEncoder(left, right);
		distanceEncoderPID = new PIDController(kP, kI, kD, avgEncoderPIDSource, distanceEncoderPIDOutput);
		distanceEncoderPID.setOutputRange(-0.3, 0.3);

		turningGyroPIDSource = new PIDSourceGyro();
		turningGyroPID = new PIDController(tP, tI, tD, turningGyroPIDSource, turningGyroPIDOutput);
		turningGyroPID.setOutputRange(-0.3, 0.3);
	}

	@Override
	protected void initialize() {
		System.out.println("Init GyroEncoderDriveDistance");
		left.reset();
		right.reset();
		turningGyroPID.setSetpoint(0.0);
		distanceEncoderPID.setSetpoint(encoderSetpoint);
		distanceEncoderPID.enable();
		turningGyroPID.enable();

		distanceEncoderError = encoderSetpoint - (left.getDistance() + right.getDistance()) / 2;
		turningEncoderError = Math.abs(0 - turningGyroPIDOutput.getOutput());
	}

	@Override
	protected void execute() {
		drive.arcadeDrive(distanceEncoderPIDOutput.getOutput(), turningGyroPIDOutput.getOutput());
		distanceEncoderError = encoderSetpoint - (left.getDistance() + right.getDistance());
		turningEncoderError = Math.abs(0 - turningGyroPIDOutput.getOutput());
		System.out.println("Left: " + left.getDistance());
		System.out.println("Right: " + right.getDistance());
		System.out.println("PID output: " + distanceEncoderPIDOutput.getOutput());
		System.out.println("Error: " + distanceEncoderError);
		System.out.println("Setpoint: " + encoderSetpoint);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (distanceEncoderError > threshold && turningEncoderError > turningThreshold);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		distanceEncoderPID.disable();
		turningGyroPID.disable();
		drive.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		System.out.println("Interrupted");
		end();
	}

}