package jono.ai;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public Direction left() {
        if (this == NORTH) {
            return WEST;
        } else if (this == WEST) {
            return SOUTH;
        } else if (this == SOUTH) {
            return EAST;
        } else {
            return WEST;
        }
    }

    public Direction right() {
        if (this == NORTH) {
            return EAST;
        } else if (this == WEST) {
            return NORTH;
        } else if (this == SOUTH) {
            return WEST;
        } else {
            return SOUTH;
        }
    }
}
