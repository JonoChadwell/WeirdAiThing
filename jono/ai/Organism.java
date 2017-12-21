package jono.ai;

import java.util.ArrayList;
import java.util.List;

public class Organism {
    private static class Connection {
        private Cell from;
        private Cell to;

        public Connection(Cell from, Cell to) {
            this.from = from;
            this.to = to;
        }

        public void update() {
            to.writeValue(from, from.readValue(to));
        }
    }

    private List<BrainCell> brain = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    private EmitterCell turning = new EmitterCell();
    private EmitterCell moving = new EmitterCell();
    private EmitterCell eating = new EmitterCell();
    private EmitterCell resting = new EmitterCell();

    private SensorCell food = new SensorCell();
    private SensorCell water = new SensorCell();
    private SensorCell others = new SensorCell();
    private SensorCell location = new SensorCell();
    private SensorCell hunger = new SensorCell();
    private SensorCell thirst = new SensorCell();
    private SensorCell energy = new SensorCell();

    private Direction facing = Direction.NORTH;
    private Location currentLocation;

    public Organism(long seed, Location startLocation) {
        BrainCell bc = new BrainCell(seed);

        currentLocation = startLocation;

        brain.add(bc);

        connections.add(new Connection(bc, turning));
        connections.add(new Connection(bc, moving));
        connections.add(new Connection(bc, eating));
        connections.add(new Connection(bc, resting));

        connections.add(new Connection(food, bc));
        connections.add(new Connection(water, bc));
        connections.add(new Connection(others, bc));
        connections.add(new Connection(location, bc));
        connections.add(new Connection(hunger, bc));
        connections.add(new Connection(thirst, bc));
        connections.add(new Connection(energy, bc));
    }

    public void update() {
        location.setSensor(currentLocation.getId());
        food.setSensor(currentLocation.getFood());
        water.setSensor(currentLocation.getWater());

        for (Connection c : connections) {
            c.update();
        }
        for (BrainCell bc : brain) {
            bc.run(1000);
        }
        if (turning.getEmittedValue() < 0) {
            System.out.println("Turning to the left");
            facing = facing.left();
        } else if (turning.getEmittedValue() > 0) {
            System.out.println("Turning to the right");
            facing = facing.right();
        }
        if (moving.getEmittedValue() > 0) {
            System.out.println("Moving to the " + facing);
            currentLocation = currentLocation.locationToThe(facing);
        }
        System.out.println("Currently at location " + currentLocation.getId());
    }
}
