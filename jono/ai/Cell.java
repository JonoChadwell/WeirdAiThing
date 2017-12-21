package jono.ai;

public abstract class Cell {
    public abstract int readValue(Cell dest);
    public abstract void writeValue(Cell source, int value);
}
