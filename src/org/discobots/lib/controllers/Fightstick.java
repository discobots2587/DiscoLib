package org.discobots.lib.controllers;

import edu.wpi.first.wpilibj.Joystick;

//Fightstick is the controller with a lot of buttons that looks like an arcade game, uses the DPAD mode
public class Fightstick extends Joystick {

	public static final int DEFAULT_USB_PORT = 1;

	public String name = "Fightstick";

	public static int AXIS_LX = 0;
	public static int AXIS_LY = 1;

	public static int AXIS_L2 = 2;
	public static int AXIS_R2 = 3;

	// Fightstick doesn't have the RX or RY axes

	public static int BTN_A = 1;
	public static int BTN_B = 2;
	public static int BTN_X = 3;
	public static int BTN_Y = 4;
	public static int BTN_L1 = 5;
	public static int BTN_R1 = 6;
	public static int BTN_SHARE = 7;
	public static int BTN_OPTIONS = 8;
	public static int BTN_L3 = 9;
	public static int BTN_R3 = 10;

	/**
	 * Creates a fightstick with name "Fightstick" in USB port <i>port</i>
	 * 
	 * @param port
	 *            USB port
	 */
	public Fightstick(int port) {
		super(port);
	}

	/**
	 * Creates a fightstick with name <i>name</i> in USB port <i>port</i>
	 * 
	 * @param port
	 *            USB port
	 * @param name
	 *            Fightstick name
	 */
	public Fightstick(int port, String name) {
		super(port);
		this.name = name;
	}

	/**
	 * Creates a fightstick with name "Fightstick" in USB port 1
	 */
	public Fightstick() {
		super(DEFAULT_USB_PORT);
	}

	@Override
	public String getName() {
		return this.name;
	}

	public double getLX() {
		return this.getRawAxis(AXIS_LX);
	}

	public double getLY() {
		return this.getRawAxis(AXIS_LY);
	}

	public double getL2() {
		return this.getRawAxis(AXIS_L2);
	}

	public double getR2() {
		return this.getRawAxis(AXIS_R2);
	}

	// getDPAD() is more precise than its Gamepad counterpart because it uses a
	// joystick
	public double getDPAD() {
		double val = this.getPOV();
		/*
		 * if(0 < val && val < 67.5) { val = 45; } else if(67.5 <= val && val < 112.5) {
		 * val = 90; } else if(112.5 <= val && val < 157.5) { val = 135; } else if(157.5
		 * <= val && val < 202.5) { val = 180; } else if(202.5 <= val && val < 247.5) {
		 * val = 225; } else if(247.5 <= val && val < 225) { val = 270; } else if(225 <=
		 * val && val < 347.5) { val = 315; } else { val = 0; }
		 */
		return val;
	}
}
