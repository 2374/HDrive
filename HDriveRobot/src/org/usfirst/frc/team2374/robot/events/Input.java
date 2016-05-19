package org.usfirst.frc.team2374.robot.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.usfirst.frc.team2374.robot.Command;

import edu.wpi.first.wpilibj.Joystick;

public class Input {

	/**
	 * The joysticks
	 */
	public static final Joystick JOYSTICK1 = new Joystick(0),
			JOYSTICK2 = new Joystick(1);

	/*
	 * These maps store internal data about when buttons are being pressed.
	 */
	private static final Map<Integer, Boolean> BUTTON_VALS = new HashMap();
	private static final Map<Integer, EventStream> BUTTON_PRESSED_EVENTSTREAMS = new HashMap();
	private static final Map<Integer, EventStream> BUTTON_RELEASED_EVENTSTREAMS = new HashMap();

	/**
	 * Returns the value of a joystick axis. Is equivalent to
	 * Joystick.getRawAxis().
	 *
	 * @param axis
	 *            The axis of the joystick to read.
	 * @return The amount the axis is pushed on the joystick.
	 */
	public static double getAxis(int axis) {
		return JOYSTICK1.getRawAxis(axis);
	}

	/**
	 * Returns the value returned from an axis from the second joystick
	 * controller.
	 * 
	 * @param axis
	 * @return
	 */
	public static double getAxis2(int axis) {
		return JOYSTICK2.getRawAxis(axis);
	}

	/**
	 * Returns whether a button is held down. Is equivalent to
	 * Joystick.getRawButton().
	 *
	 * @param button
	 *            The button of the joystick to read.
	 * @return Whether the button is held down on the joystick.
	 */
	public static boolean getButton(int button) {
		return JOYSTICK1.getRawButton(button);
	}

	/**
	 * getButton() method configured for the second controller. Equivalent in
	 * pretty much every sense getButton();
	 * 
	 * @param button
	 * @return
	 */
	public static boolean getButton2(int button) {
		return JOYSTICK2.getRawButton(button);
	}

	/**
	 * This function reads joystick input and send out events as needed.
	 */
	public static void update() {
		for (Entry<Integer, Boolean> e : BUTTON_VALS.entrySet()) {
			if (!e.getValue() && JOYSTICK1.getRawButton(e.getKey())) {
				BUTTON_PRESSED_EVENTSTREAMS.get(e.getKey()).sendEvent();
			}
			if (e.getValue() && !JOYSTICK1.getRawButton(e.getKey())) {
				BUTTON_RELEASED_EVENTSTREAMS.get(e.getKey()).sendEvent();
			}
			e.setValue(JOYSTICK1.getRawButton(e.getKey()));
		}
	}

	/**
	 * This function returns an EventStream that has an event every time a given
	 * button is pressed.
	 *
	 * @param button
	 *            The button to be pressed.
	 * @return An EventStream that has an event every time the button is
	 *         pressed.
	 */
	public static EventStream whenPressed(int button) {
		BUTTON_VALS.putIfAbsent(button, JOYSTICK1.getRawButton(button));
		BUTTON_PRESSED_EVENTSTREAMS.putIfAbsent(button, new EventStream());
		BUTTON_RELEASED_EVENTSTREAMS.putIfAbsent(button, new EventStream());

		return BUTTON_PRESSED_EVENTSTREAMS.get(button);
	}

	/**
	 * This function returns an EventStream that has an event every time a given
	 * button is released.
	 *
	 * @param button
	 *            The button to be released.
	 * @return An EventStream that has an event every time the button is
	 *         released.
	 */
	public static EventStream whenReleased(int button) {
		BUTTON_VALS.putIfAbsent(button, JOYSTICK1.getRawButton(button));
		BUTTON_PRESSED_EVENTSTREAMS.putIfAbsent(button, new EventStream());
		BUTTON_RELEASED_EVENTSTREAMS.putIfAbsent(button, new EventStream());

		return BUTTON_RELEASED_EVENTSTREAMS.get(button);
	}

	/**
	 * This function makes a command toggle on and off based on whether a button
	 * is pressed. When the button is pressed, the command starts. When the
	 * button is released, the command ends.
	 *
	 * @param button
	 *            The button that stops and starts the command.
	 * @param command
	 *            The command to be toggled by the button.
	 */
	public static void whileHeld(int button, Command command) {
		whenPressed(button).runCommand(command);
		whenReleased(button).stopCommand(command);
	}
}
