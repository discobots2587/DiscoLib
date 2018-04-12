package org.discobots.lib.autonomous.commands;

import org.discobots.lib.autonomous.pid.EncoderPID;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class TankEncoderDrive extends Command implements EncoderPID {
	// time = drive duration in milliseconds
	// endTime = when the system reaches this time, it will stop driving
	// finished = will become true when the time is reached
	private DifferentialDrive drive;
	private Encoder left, right;

	private double leftDistance, rightDistance;
	private double leftDistanceEnd, rightDistanceEnd;
	private double speed;
	private boolean leftComplete, rightComplete;
	private double maxDistance;

	// speed is forward speed, rotation is y rotation, distance is in ticks(?)
	/**
	 * 
	 * @param speed
	 *            Forward speed
	 * @param leftDistance
	 *            Distance in inches for the left side
	 * @param rightDistance
	 *            Distance in inches for the right side
	 */
	public TankEncoderDrive(double speed, double leftDistance, double rightDistance) {
		this.speed = speed;
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;

		drive = getDrivetrain();
		left = getLeftEncoder();
		right = getRightEncoder();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		leftDistanceEnd = left.getDistance() + leftDistance;
		rightDistanceEnd = right.getDistance() + rightDistance;
		leftComplete = rightComplete = false;
		maxDistance = Math.max(leftDistance, rightDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// scale each side's speed and set the drive to it
		double leftSpeed = this.speed * (leftDistance / maxDistance);
		double rightSpeed = this.speed * (rightDistance / maxDistance);
		drive.tankDrive(leftSpeed, rightSpeed);

		if (leftDistance <= 0) {
			leftComplete = (left.getDistance() <= leftDistanceEnd);
		} else {
			leftComplete = (right.getDistance() >= leftDistanceEnd);
		}

		if (rightDistance <= 0) {
			rightComplete = (left.getDistance() <= rightDistanceEnd);
		} else {
			rightComplete = (right.getDistance() >= rightDistanceEnd);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return leftComplete && rightComplete;
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
}
