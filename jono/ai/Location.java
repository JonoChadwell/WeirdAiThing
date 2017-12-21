package jono.ai;

public class Location {
    private Location next;
    private Location previous;

    private final int id;

    public Location(int id) {
        this.id = id;
    }

    public int getFood() {
        return 0;
    }

    public int getWater() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public Location locationToThe(Direction direction) {
        if (direction == Direction.EAST && next != null) {
            return next;
        } else if (direction == Direction.WEST  && previous != null) {
            return previous;
        } else {
            return this;
        }
    }

    public void setNext(Location next) {
        this.next = next;
    }

    public void setPrevious(Location previous) {
        this.previous = previous;
    }
}
