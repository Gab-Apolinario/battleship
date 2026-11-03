package battleship.model;

import java.util.List;

public abstract class Ship {
    private int id;
    private String name;
    private char symbol;
    private int size;
    private Orientation orientation;
    private int startCol;
    private int startRow;
    private boolean[] hitSegments;
    private boolean placed;
    private boolean sunk;

    public Ship(int id, String name, char symbol, int size, Orientation orientation) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.size = size;
        this.orientation = orientation;
        startCol = 0;
        startRow = 0;
        this.placed = false;
        this.sunk = false;
        
        hitSegments = new boolean[size];
    }

    public void registerHit(int segmentIndex) {

    }

    public boolean occupies(int row, int col) {
        return false;
    }

    public int segmentIndexAt(int row, int col) {
        return 0;
    }
    
    public boolean isSegmentHit(int segmentIndex) {
        return false;

    }

    public void setPosition(int row, int col, Orientation orientation) {

    }

    public void setOrientation(Orientation orientation) {

    }

    public List<String> getCoordinates() {
        return null;
    }

    public int getHits() {
        return 0;
    }

    public boolean isSunk() {
        return false;
    }

    public int getSize() {
        return 0;
    }

    public abstract String getDescription();
}