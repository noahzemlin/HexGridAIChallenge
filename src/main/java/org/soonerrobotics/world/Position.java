package org.soonerrobotics.world;

public class Position {
    private short x;
    private short y;

    public static final Position RIGHT = new Position(2, 0);
    public static final Position LEFT = new Position(-2, 0);
    public static final Position UP_LEFT = new Position(-1, -1);
    public static final Position UP_RIGHT = new Position(1, -1);
    public static final Position DOWN_LEFT = new Position(-1, 1);
    public static final Position DOWN_RIGHT = new Position(1, 1);
    public static final Position[] moves = new Position[]{RIGHT, LEFT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};

    public Position(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public void setX(short x) {
        this.x = x;
    }

    public void setY(short y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    public Position add(Position position) {
        return new Position((short) (this.x + position.getX()), (short) (this.y + position.getY()));
    }
}
