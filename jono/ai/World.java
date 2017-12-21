package jono.ai;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static final int WORLD_SIZE = 100;
    Location start;
    List<Organism> inhabitants = new ArrayList<>();

    public World() {
        Location last_place = null;
        start = new Location(0);
        last_place = start;
        for (int i = 1; i < WORLD_SIZE; i++) {
            Location new_place = new Location(i);
            new_place.setPrevious(last_place);
            last_place.setNext(new_place);
            last_place = new_place;
        }
    }

    private Location getStartLocation() {
        return start;
    }

    public void addOrganism(long seed) {
        inhabitants.add(new Organism(seed, getStartLocation()));
    }

    public void update() {
        for (Organism critter : inhabitants) {
            critter.update();
        }
    }
}
