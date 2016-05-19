package org.usfirst.frc.team2374.robot;

import java.util.function.Supplier;

import org.usfirst.frc.team2374.robot.controllers.DisabledController;
import org.usfirst.frc.team2374.robot.controllers.TeleopController;
import org.usfirst.frc.team2374.robot.controllers.TestController;

public abstract class Controller {

	/**
	 * This function is called once when the controller starts.
	 */
	public abstract void start();

	/**
	 * This enum lists all the types of controllers. It is used internally to
	 * determine which controller to run.
	 */
	public static enum ControllerType {

		DISABLED(DisabledController::new), TELEOP(TeleopController::new), TEST(
				TestController::new);

		public static ControllerType getType() {
			if (Robot.robot.isDisabled()) {
				return DISABLED;
			}
			if (Robot.robot.isOperatorControl()) {
				return TELEOP;
			}
			if (Robot.robot.isTest()) {
				return TEST;
			}
			throw new RuntimeException("Unknown robot state");
		}

		private final Supplier<Controller> creator;

		private ControllerType(Supplier<Controller> creator) {
			this.creator = creator;
		}

		public Controller create() {
			return creator.get();
		}
	}
}
