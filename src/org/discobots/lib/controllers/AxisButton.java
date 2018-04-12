package org.discobots.lib.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class AxisButton extends Button {

	private Joystick js;
	private double triggerValue;
	private boolean greaterThan;
	private int port;

	/**
	 * AxisButton from axis port <i>port</i> of Joystick <i>js</i>
	 * <p>
	 * AxisButton is triggered if greater/less than or equal to trigger value
	 * <i>tValue</i> depending on <i>gThan</i>
	 * 
	 * @param js
	 *            Joystick
	 * @param port
	 *            Axis port
	 * @param tValue
	 *            Trigger value threshold
	 * @param gThan
	 *            Greater or less than trigger value
	 */
	public AxisButton(Joystick js, int port, double tValue, boolean gThan) {
		this.port = port;
		this.js = js;
		this.triggerValue = tValue;
		this.greaterThan = gThan;
	}

	/**
	 * AxisButton from axis port <i>port</i> of Joystick <i>js</i>
	 * <p>
	 * AxisButton is triggered if greater than or equal to trigger value
	 * <i>tValue</i>
	 * 
	 * @param js
	 *            Joystick
	 * @param port
	 *            USB port
	 * @param tValue
	 *            Trigger value threshold
	 */
	public AxisButton(Joystick js, int port, double tValue) {
		this(js, port, tValue, true);
	}

	/**
	 * AxisButton from axis port <i>port</i> of Joystick <i>js</i>
	 * <p>
	 * AxisButton is triggered if greater than or equal to 1
	 * 
	 * @param js
	 *            Joystick
	 * @param port
	 *            USB port
	 */
	public AxisButton(Joystick js, int port) {
		this(js, port, 1, true);
	}

	@Override
	public boolean get() {
		if (greaterThan)
			return (Math.abs(js.getRawAxis(port)) >= triggerValue);
		else
			return (Math.abs(js.getRawAxis(port)) <= triggerValue);
	}

}
