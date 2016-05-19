package org.usfirst.frc.team2374.robot.controllers;

import org.usfirst.frc.team2374.robot.Controller;
import org.usfirst.frc.team2374.robot.commands.HDriveCommand;

public class TeleopController extends Controller {

	@Override
	public void start() {
		// This controls the robot's wheels
		new HDriveCommand().startAsDefaultCommand();
		// Make the robot go forward for 1 second on the A button
		// Input.whenPressed(1).runCommand(new ForwardsCommand(1));
	}
}
