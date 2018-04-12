package org.discobots.lib.autonomous.pid;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public interface GyroPID {

	abstract DifferentialDrive getDrivetrain();

	abstract double getYaw();
}
