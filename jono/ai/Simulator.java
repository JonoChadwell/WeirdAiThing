package jono.ai;

public class Simulator {

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        while (true) {
            sim.tick();
        }
    }
}
