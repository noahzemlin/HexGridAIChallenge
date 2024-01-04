package org.soonerrobotics.actions;

import org.soonerrobotics.agent.Agent;
import org.soonerrobotics.world.Position;
import org.soonerrobotics.world.World;

public class MoveAction extends AbstractAction{

    private short step_x = 0;
    private short step_y = 0;

    public MoveAction(short step_x, short step_y) {
        this.step_x = step_x;
        this.step_y = step_y;
    }

    public MoveAction(Position move) {
        this.step_x = move.getX();
        this.step_y = move.getY();
    }

    @Override
    public void doAction(World world, Agent agent) {
        world.moveAgent(agent, this.step_x, this.step_y);
    }
}
