package jono.ai;

public class SensorCell extends Cell {
    private int value = 0;

    @Override
    public int readValue(Cell dest) {
        return value;
    }

    @Override
    public void writeValue(Cell source, int value) {
        throw new RuntimeException("Value written to sensor cell");
    }

    public void setSensor(int value) {
        this.value = value;
    }
}
