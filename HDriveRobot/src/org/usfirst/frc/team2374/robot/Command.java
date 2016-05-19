package org.usfirst.frc.team2374.robot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Command {

    /**
     * Lists of Runnables to execute when this command starts or ends.
     */
    protected final List<Runnable> onFinish = new LinkedList<>(), onStart = new LinkedList<>();

    /**
     * Whether the command is currently running.
     */
    private boolean isRunning;

    /**
     * This function creates a command that runs continuously and that requires
     * no components.
     *
     * @param r A runnable that the command calls each update.
     * @return The continuous command.
     */
    public static Command continuousCommand(Runnable r) {
        return new Command() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public List<Component> requires() {
                return Arrays.asList();
            }

            @Override
            public void update() {
                r.run();
            }
        };
    }

    /**
     * Calling this function ends the command.
     */
    public void finish() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        for (Component component : requires()) {
            component.command = null;
            if (component.defaultCommand != null) {
                component.defaultCommand.start();
            }
        }
        Robot.COMMANDS.remove(this);
        for (Runnable r : onFinish) {
            r.run();
        }
    }

    /**
     * This function returns true when the command should end.
     *
     * @return Whether to end the command.
     */
    public abstract boolean isFinished();

    /**
     * This function determines which commands take priority over others.
     *
     * @return The priority of the command.
     */
    public double priority() {
        return 0;
    }

    /**
     * This function returns the list of components the command requires.
     *
     * @return The list of RobotComponents.
     */
    public abstract List<Component> requires();

    /**
     * Calling this function starts the command.
     */
    public void start() {
        if (isRunning) {
            return;
        }
        //Check to see if any components you need are currently being used by other commands.
        for (Component component : requires()) {
            if (component.command != null) {
                if (component.command.priority() > priority()) {
                    return;
                }
            }
        }
        isRunning = true;
        for (Component component : requires()) {
            if (component.command != null) {
                component.command.finish();
            }
            component.command = this;
        }
        Robot.COMMANDS.add(this);
        for (Runnable r : onStart) {
            r.run();
        }
    }

    /**
     * Calling this function starts the command and sets it as the default
     * command of all of its components.
     */
    public void startAsDefaultCommand() {
        for (Component component : requires()) {
            component.defaultCommand = this;
        }
        start();
    }

    /**
     * This function tells another command to run once this one has ended.
     *
     * @param command The command to run after this one.
     * @return Returns the other command for easy chaining.
     */
    public Command thenRun(Command command) {
        onFinish.add(() -> command.start());
        return command;
    }
    
    public String toString() {
    	return getClass().getSimpleName();
    }

    /**
     * This function is called continuously while the command is running.
     */
    public abstract void update();
}
