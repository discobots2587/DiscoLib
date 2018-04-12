package org.discobots.lib.autonomous.commands;

import org.discobots.lib.autonomous.pid.GyroPID;
import org.discobots.lib.autonomous.pid.PIDDummyOutput;
import org.discobots.lib.autonomous.pid.PIDSourceGyro;
import org.discobots.lib.util.Debugger;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class ArcadeGyroTurn extends Command implements GyroPID {

	private DifferentialDrive drive;

	private double turningThreshold;
	private double turningSetpoint;

	private PIDDummyOutput turningGyroPIDOutput;
	private PIDController turningGyroPID;
	private PIDSourceGyro turningGyroPIDSource;

	private double kP;
	private double kI;
	private double kD;
	private double preError;
	private double error;
	double integral;
	boolean right;

	public ArcadeGyroTurn(double turningSetpoint, double turningThreshold, double kP, double kI, double kD,
			boolean right) {
		this(turningSetpoint, turningThreshold, kP, kI, kD, 50.0, right);
	}

	public ArcadeGyroTurn(double turningSetpoint, double turningThreshold, double kP, double kI, double kD,
			double period) {
		this(turningSetpoint, turningThreshold, kP, kI, kD, period, false);
	}

	public ArcadeGyroTurn(double turningSetpoint, double turningThreshold, double kP, double kI, double kD,
			double period, boolean right) {
		if (right) {
			this.turningSetpoint = getYaw() + turningSetpoint;
		} else {
			this.turningSetpoint = getYaw() - turningSetpoint;
		}
		this.right = right;
		turningGyroPIDOutput = new PIDDummyOutput();
		turningGyroPIDSource = new PIDSourceGyro();
		turningGyroPID = new PIDController(kP, kI, kD, turningGyroPIDSource, turningGyroPIDOutput, period);
		turningGyroPID.setOutputRange(-0.7, 0.7);
		this.error = 0.0;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.integral = 0;
		this.preError = 0;

		drive = getDrivetrain();
	}

	@Override
	protected void initialize() {
		turningGyroPID.enable();
		turningGyroPID.setSetpoint(turningSetpoint);
	}

	@Override
	protected void execute() {
		error = Math.abs(turningSetpoint - getYaw());

		this.integral = this.integral + (error * 0.004);
		// determine the amount of change from the last time checked
		double derivative = (error - preError) / 0.004;
		// calculate how much to drive the output in order to get to the
		// desired setpoint.
		double output = (this.kP * error) + (this.kI * integral) + (this.kD * derivative);
		// remember the error for the next time around.
		preError = error;

		/*
		 * if(output > 0.5) { output = 0.5; } else if (output < -0.5) { output = -0.5; }
		 */

		if (right)
			drive.arcadeDrive(0, output);
		else
			drive.arcadeDrive(0, -output);

		Debugger.getInstance().log("PID output: " + output, "PID-OUTPUT");
		Debugger.getInstance().log("Error TURNING: " + turningGyroPID.getError(), "PID-ERROR");
		Debugger.getInstance().log("Current Heading " + getYaw(), "YAW");
		Debugger.getInstance().log("Setpoint: " + turningSetpoint, "PID-SETPOINT");
	}

	@Override
	protected boolean isFinished() {
		return (turningGyroPID.getError() < Math.abs(turningThreshold));
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		turningGyroPID.disable();
		drive.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}