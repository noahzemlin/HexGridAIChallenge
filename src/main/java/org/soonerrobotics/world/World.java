package org.soonerrobotics.world;

import org.soonerrobotics.agent.Agent;

import java.util.*;

public class World {
    // https://www.redblobgames.com/grids/hexagons/
    // using “double-width” horizontal layout, doubles column values
    // (0,0) is top-left. (0,5) is bottom left. (5,0) is top right.
    private final short width;
    private final short height;
    protected Map<String, Tile> tileMap;
    protected List<Agent> agents;

    protected World(short width, short height) {
        this.width = width;
        this.height = height;
        this.tileMap = new HashMap<String, Tile>();
        this.agents = new ArrayList<>();
    }

    public Tile getTile(String id) {
        return tileMap.get(id);
    }

    public List<Agent> getAgents() {
        return List.copyOf(agents);
    }

    public boolean moveAgent(Agent agent, short step_x, short step_y) {
        Position newPosition = getWrappedPosition(agent.getPosition().add(new Position(step_x, step_y)));
        if (newPosition == null) {
            return false;
        }

        agent.moveAgent(newPosition);
        return true;
    }

    public Position getWrappedPosition(Position position) {
        if (position.getY() < 0 || position.getY() >= this.height)
            return null;

        return new Position((short) (position.getX() % this.width), position.getY());
    }
}
