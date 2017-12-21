package jono.ai;

import java.util.ArrayList;
import java.util.List;

public class World {
    Location theOnlyPlaceInTheWorld = new Location();
    List<Organism> inhabitants = new ArrayList<>();

    private Location getStartLocation() {
        return theOnlyPlaceInTheWorld;
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
