package org.soonerrobotics.clients;

import org.soonerrobotics.actions.AbstractAction;

public abstract class Client {
    public abstract void initialize();
    public abstract AbstractAction step();
}
