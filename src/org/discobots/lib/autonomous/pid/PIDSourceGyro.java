package org.discobots.lib.autonomous.pid;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class PIDSourceGyro implements PIDSource, GyroPID {

	public PIDSourceGyro() {

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
		return getYaw();
	}

	@Override
	public double getYaw() {
		return 0;
		// TODO Auto-generated method stub
	}

	@Override
	public DifferentialDrive getDrivetrain() {
		// TODO Auto-generated method stub
		return null;
	}

}