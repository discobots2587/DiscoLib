package org.discobots.lib.autonomous;

import org.discobots.lib.Position;
import org.discobots.lib.autonomous.commands.Nothing;
import org.discobots.lib.util.Debugger;

import edu.wpi.first.wpilibj.command.Command;

public abstract class AutonChooser extends Command {

	protected Command autonCommand;

	@Override
	protected void initialize() {
		gameSpecificInit();

		switch (getPosition()) {
		case LEFT:
			left();
			break;
		case CENTER:
			center();
			break;
		case RIGHT:
			right();
			break;
		default:
			Debugger.getInstance().log("Robot position was not selected!", "Auton");
			autonCommand = new Nothing();
			break;
		}

		autonCommand.start();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	protected abstract void gameSpecificInit();

	protected abstract Position getPosition();

	protected abstract void left();

	protected abstract void right();

	protected abstract void center();
}
