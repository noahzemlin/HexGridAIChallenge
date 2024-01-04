package org.soonerrobotics.actions;

import org.soonerrobotics.agent.Agent;
import org.soonerrobotics.world.World;

public abstract class AbstractAction {
    public abstract void doAction(World world, Agent agent);
}
