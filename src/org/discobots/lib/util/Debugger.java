package org.discobots.lib.util;

/* Based off of 1114's 2015 Debugger */

public class Debugger {

	private static Debugger instance;

	public static Debugger getInstance() {
		if (instance == null) {
			instance = new Debugger();
		}
		return instance;
	}

	public void log(String msg, String flag) {
		System.out.println("[" + flag + "] " + msg);
	}

	public void log(String msg) {
		log(msg, "DEBUG");
	}

	public void log(int msg, String flag) {
		log("" + msg, flag);
	}

	public void log(int msg) {
		log("" + msg);
	}

	public void log(double msg, String flag) {
		log("" + msg, flag);
	}

	public void log(double msg) {
		log("" + msg);
	}

	public void log(float msg, String flag) {
		log("" + msg, flag);
	}

	public void log(float msg) {
		log("" + msg);
	}

	public void log(long msg, String flag) {
		log("" + msg, flag);
	}

	public void log(long msg) {
		log("" + msg);
	}

	public void log(boolean msg, String flag) {
		log("" + msg, flag);
	}

	public void log(boolean msg) {
		log("" + msg);
	}
}
