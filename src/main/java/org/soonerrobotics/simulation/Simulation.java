package org.soonerrobotics.simulation;

import org.soonerrobotics.agent.Agent;
import org.soonerrobotics.world.World;

public class Simulation {
    private World world;
    public Simulation(World world) {
        this.world = world;
    }

    public void step() {
        for (Agent agent : world.getAgents()) {
            agent.step();
        }
    }
}
