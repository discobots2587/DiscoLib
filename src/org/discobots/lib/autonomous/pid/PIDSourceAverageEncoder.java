package org.discobots.lib.autonomous.pid;

import org.discobots.lib.util.Util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDSourceAverageEncoder implements PIDSource {

	private Encoder left, right;
	private PIDSourceType pidSource;

	public PIDSourceAverageEncoder() {

	}

	public PIDSourceAverageEncoder(Encoder left, Encoder right) {
		System.out.println("AverageEncoderPIDSource");
		this.left = left;
		this.right = right;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		this.pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return pidSource;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		// System.out.println("right get distance " + right.getDistance());
		// return Robot.drive.m_right_encoder.getDistance();
		// return (left.getDistance() + right.getDistance()) / 2;
		return Util.encoderAvg(left.getDistance(), right.getDistance());
	}

}
