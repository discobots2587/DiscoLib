package org.discobots.lib.autonomous.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public interface EncoderPID {

	abstract DifferentialDrive getDrivetrain();

	abstract Encoder getLeftEncoder();

	abstract Encoder getRightEncoder();
}
