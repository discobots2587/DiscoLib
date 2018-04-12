package org.discobots.lib.autonomous.pid;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * A dummy PID output that does not output to any hardware. Pass the
 * DummyPIDOutput object to the PIDController class and read the output in your
 * own code. This is useful for mixing the results of multiple PID controllers
 * to one output.
 *
 * @author Joe Ross, Team 330
 */
public class PIDDummyOutput implements PIDOutput {
	double output;

	public PIDDummyOutput() {
		output = 0;
	}

	public void pidWrite(double output) {
		// Debugger.getInstance().log("Output: "+output,"PID-OUTPUT");
		this.output = output;
	}

	public double getOutput() {
		return output;
	}

}