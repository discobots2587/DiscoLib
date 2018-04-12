package org.discobots.lib.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DpadButton extends Button {

	private Joystick js;
	private POV pov;
	private boolean locked;

	public enum POV {
		UP(0), DOWN(180), RIGHT(90), LEFT(270), UP_RIGHT(45), DOWN_RIGHT(135), DOWN_LEFT(225), UP_LEFT(315);

		private final double val;

		private POV(double val) {
			this.val = val;
		}
	}

	/**
	 * DpadButton from POV direction <i>pov</i> of Joystick <i>js</i>
	 * <p>
	 * DpadButton can only be Up, Down, Left, Right
	 * 
	 * @param js
	 *            Joystick
	 * @param pov
	 *            pov enum
	 */
	public DpadButton(Joystick js, POV pov) {
		this(js, pov, true);
	}

	/**
	 * DpadButton from POV direction <i>pov</i> of Joystick <i>js</i>
	 * <p>
	 * If locked is true, DpadButton can only be Up, Down, Left, Right
	 * <p>
	 * If locked is false, DpadButton can only be Up, Down, Left, Right, Up_Right,
	 * Up_Left, Down_Right, Down_Left
	 * 
	 * @param js
	 *            Joystick
	 * @param pov
	 *            pov enum
	 * @param locked
	 *            Locked boolean (only Up, Down, Left, Right)
	 */
	public DpadButton(Joystick js, POV pov, boolean locked) {
		this.js = js;
		this.pov = pov;

	}

	@Override
	public boolean get() {
		double val = this.pov.val;
		double currentVal = js.getPOV();
		if (this.locked)
			currentVal = Gamepad.getDPADfromPOV(currentVal);
		if (currentVal == val) {
			return true;
		}
		return false;
	}

}
