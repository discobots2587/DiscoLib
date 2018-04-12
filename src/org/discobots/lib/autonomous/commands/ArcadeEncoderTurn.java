package org.discobots.lib.autonomous.commands;

import org.discobots.lib.autonomous.pid.EncoderPID;
import org.discobots.lib.util.Debugger;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class ArcadeEncoderTurn extends Command implements EncoderPID {

	private DifferentialDrive drive;
	private Encoder left;
	private Encoder right;

	private double threshold;
	private double turnSetpoint;

	private double turningEncoderError;

	private boolean right_turn;

	private double kP;
	private double kI;
	private double kD;
	private double preError;

	double integral;

	/**
	 * 
	 * @param turnSetpoint
	 *            Turning setpoint in degrees
	 * @param threshold
	 *            Error threshold
	 * @param kP
	 *            Proportional value
	 * @param kI
	 *            Integral value
	 * @param kD
	 *            Derivative value
	 * @param right_turn
	 *            right = true, left = false
	 */
	public ArcadeEncoderTurn(double turnSetpoint, double threshold, double kP, double kI, double kD,
			boolean right_turn) {
		System.out.println("EncoderDriveDistanceTurningComp Starting");

		drive = getDrivetrain();
		left = getLeftEncoder();
		right = getRightEncoder();

		// distanceEncoderPID = new
		// PIDController(kP,kI,kD,avgEncoderPIDSource,distanceEncoderPIDOutput);

		this.turnSetpoint = turnSetpoint;
		this.threshold = threshold;
		this.right_turn = right_turn;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.integral = 0;
		this.preError = 0;
	}

	@Override
	protected void initialize() {
		left.reset();
		right.reset();

		this.turningEncoderError = turnSetpoint - ((right.getDistance() - left.getDistance()) / getTurningFactor());

	}

	@Override
	protected void execute() {
		/*
		 * if(!distanceEncoderPID.isEnabled()) { distanceEncoderPID.enable(); }
		 */

		this.integral = this.integral + (turningEncoderError * 0.004);
		// determine the amount of change from the last time checked
		double derivative = (turningEncoderError - preError) / 0.004;
		// calculate how much to drive the output in order to get to the
		// desired setpoint.
		double output = (this.kP * turningEncoderError) + (this.kI * integral) + (this.kD * derivative);
		// remember the error for the next time around.
		preError = turningEncoderError;

		if (right_turn)
			drive.arcadeDrive(0.0, -0.9);
		else
			drive.arcadeDrive(0.0, 0.9);
		this.turningEncoderError = turnSetpoint - ((right.getDistance() - left.getDistance()) / getTurningFactor());
		Debugger.getInstance().log("Left: " + left.getDistance(), "PID-ENCODER");
		Debugger.getInstance().log("Right: " + right.getDistance(), "PID-ENCODER");
		Debugger.getInstance().log("PID output: " + output, "PID-OUTPUT");
		Debugger.getInstance().log("Error TURNING: " + turningEncoderError, "PID-ERROR");
		Debugger.getInstance().log("Setpoint: " + turnSetpoint, "PID-SETPOINT");
		Timer.delay(0.004);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return (turningEncoderError < this.threshold);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drive.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	abstract double getTurningFactor();

}
