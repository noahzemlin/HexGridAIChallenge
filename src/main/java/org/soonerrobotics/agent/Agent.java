package org.soonerrobotics.agent;

import org.soonerrobotics.actions.AbstractAction;
import org.soonerrobotics.clients.Client;
import org.soonerrobotics.world.Position;
import org.soonerrobotics.world.World;

public abstract class Agent {

    public enum AgentType {
        Worker,
        Warrior
    }

    private final World world;
    private final AgentType type;
    private final Client client;
    private Position position;

    public Agent(World world, AgentType type, Client client) {
        this.world = world;
        this.type = type;
        this.client = client;
    }

    public AbstractAction step() {
        return this.client.step();
    }

    public void moveAgent(Position newPosition) {
        this.position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

    public World getWorld() {
        return world;
    }

    public AgentType getType() {
        return type;
    }
}
