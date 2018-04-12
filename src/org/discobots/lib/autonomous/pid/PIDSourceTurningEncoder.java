package org.discobots.lib.autonomous.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDSourceTurningEncoder implements PIDSource {

	private Encoder left;
	private Encoder right;

	public PIDSourceTurningEncoder(Encoder left, Encoder right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return (right.getDistance() - left.getDistance()) / 31.0;
	}

}
