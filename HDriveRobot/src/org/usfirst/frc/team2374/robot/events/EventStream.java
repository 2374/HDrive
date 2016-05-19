package org.usfirst.frc.team2374.robot.events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import org.usfirst.frc.team2374.robot.Command;

public class EventStream {

    private final List<EventListener> listeners = new LinkedList();

    public void addListener(EventListener el) {
        listeners.add(el);
    }

    public EventStream filter(Supplier<Boolean> supplier) {
        EventStream r = new EventStream();
        addListener(() -> {
            if (supplier.get()) {
                r.sendEvent();
            }
        });
        return r;
    }

    public void runCommand(Command command) {
        addListener(command::start);
    }

    public void sendEvent() {
        new ArrayList<>(listeners).forEach(EventListener::onEvent);
    }

    public void stopCommand(Command command) {
        addListener(command::finish);
    }
}
