package org.soonerrobotics.clients;

import org.soonerrobotics.actions.AbstractAction;
import org.soonerrobotics.actions.MoveAction;
import org.soonerrobotics.world.Position;

import java.util.Random;

public class RandomClient extends Client{

    @Override
    public void initialize() {

    }

    @Override
    public AbstractAction step() {
        int moveDirection = new Random().nextInt(6);
        return new MoveAction(Position.moves[moveDirection]);
    }
}
