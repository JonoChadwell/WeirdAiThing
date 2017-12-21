package jono.ai;

public class Simulation {

    private World world;

    public Simulation() {
        world = new World();
        world.addOrganism(0);
    }

    public void tick() {
        world.update();
    }
}
