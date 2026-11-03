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
        //marca posição como atingida
        if (hitSegments[segmentIndex] == false) {
            hitSegments[segmentIndex] = true;
        }

        boolean allHit = true; //supõe numa var local que o barco afundou e verifica em seguida
        for (boolean b : hitSegments) {            
            if (!b) {
                allHit = false; //encontrou um segmento NÃO atingido, muda a var
            }
        }
        this.sunk = allHit; //altera atributo
    }

    public boolean occupies(int row, int col) {
        return false;
    }

    public int segmentIndexAt(int row, int col) {
        return 0;
    }
    
    public boolean isSegmentHit(int segmentIndex) {
        return hitSegments[segmentIndex];
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
        return this.sunk;
    }

    public int getSize() {
        return this.size;
    }

    public abstract String getDescription();
}