package org.discobots.lib.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* Based off of 1114's 2015 Logger */

public class Logger {

	private static Logger instance;

	private BufferedWriter writer;
	private boolean logging = false;
	private final String loggerBoolean = "Logging";
	private String fileName = "";
	private final String LoggerFileName = "Logger File Name: ";
	DriverStation ds;

	private int max = 0;

	private String path;

	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	private Logger() {
		this.ds = DriverStation.getInstance();
		SmartDashboard.putBoolean(this.loggerBoolean, this.logging);
		this.logging = SmartDashboard.getBoolean(this.loggerBoolean, false);
		SmartDashboard.putString(this.LoggerFileName, this.fileName);
		this.fileName = SmartDashboard.getString(this.LoggerFileName, "");
		File f = new File("/logs");
		// File f = new File(System.getProperty("user.home"), "Desktop");
		if (!f.exists()) {
			f.mkdir();
		}

		File[] files = new File("/logs").listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					System.out.println(file.getName());
					try {
						int index = Integer.parseInt(file.getName().split("_")[0]);
						if (index > max) {
							max = index;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			max = 0;
		}
	}

	public void openFile() {
		if (this.wantToLog() || this.ds.isFMSAttached()) {
			try {
				path = this.getPath();
				this.writer = new BufferedWriter(new FileWriter(path));
				this.writer.write("");
				this.writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getPath() {
		this.fileName = SmartDashboard.getString(LoggerFileName, "");
		if (this.ds.isFMSAttached()) {
			return String.format("/logs/%d_%s_%d_log.txt", ++this.max, this.ds.getAlliance().name(),
					this.ds.getLocation());
		} else if (this.fileName != null) {
			return String.format("/logs/%d_%s.txt", ++this.max, this.fileName);
		} else {
			return String.format("/logs/%d_log.txt", ++this.max);
		}
	}

	public void logAll() {
		if (this.wantToLog()) {
			try {
				this.writer.write("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void log(String message) {
		if (this.wantToLog()) {
			try {
				this.writer.write(message + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean wantToLog() {
		this.logging = SmartDashboard.getBoolean(this.loggerBoolean, false);
		return this.logging;
	}

	public void close() {
		if (this.wantToLog()) {
			if (this.writer != null) {
				try {
					this.writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
