package jono.ai;

import java.util.HashMap;
import java.util.Map;

public class EmitterCell extends Cell {

    private Map<Cell, Integer> inputs = new HashMap<>();

    @Override
    public int readValue(Cell dest) {
        throw new RuntimeException("Value read from emitter cell");
    }

    @Override
    public void writeValue(Cell source, int value) {
        inputs.put(source, value);
    }

    public int getEmittedValue() {
        long result = 0;
        for (int value : inputs.values()) {
            result += value;
        }
        result = result / inputs.size();
        return (int) result;
    }
}
