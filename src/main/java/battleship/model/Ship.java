package battleship.model;

import java.util.ArrayList;
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
        if (segmentIndexAt(row, col) != -1) {
            return true;
        }
        return false;
    }

    //Traduz uma coordenada do tabuleiro para uma posição do array hitSegments
    public int segmentIndexAt(int row, int col) {
        //Horizontal -> row não muda
        if (orientation == Orientation.HORIZONTAL) {
            int index = col - startCol;

            //está na mesma linha | não está a esquerda do início do navio | não passou do tamanho total
            if (row == startRow && (index >= 0 && index <= size - 1)) {
                return index;
            }
        } else { //vertical -> col não muda
            int index = row - startRow;
            if (col == startCol && (index >= 0 && index <= size - 1)) {
                return index;
            }
        }
        return -1;
    }
    
    public boolean isSegmentHit(int segmentIndex) {
        return hitSegments[segmentIndex];
    }

    public void setPosition(int row, int col, Orientation orientation) {
        this.startRow = row;
        this.startCol = col;
        this.orientation = orientation;
        this.placed = true;
    }
    
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    //Devolve lista de coordenadas que o navio ocupa, em texto, como ["A1", "A2", "A3"]
    public List<String> getCoordinates() {
        //verificar se navio está posicionado, se não estiver, retorna null
        if (!placed) {
            return null;
        }
        
        //cria lista de coordenadas
        List<String> shipCoordinates = new ArrayList<>();
        
        //verifica orientação
        for (int i = 0; i <= size - 1; i++) {
            int row;
            int col;

            if (orientation == Orientation.HORIZONTAL) {
                row = startRow; //H -> trava row
                col = startCol + i; //H -> muda col
            } else {
                row = startRow + i; //V -> muda row
                col = startCol; //V -> trava col
            }

            //usar método static Board.coordinateKey(row, col) para converter
            shipCoordinates.add(Board.coordinateKey(row, col));
        }

        return shipCoordinates;
    }

    public int getHits() {
        int hits = 0; // contador de hits
        for (boolean hit : hitSegments) {
            if (hit) {
                hits++;
            }
        }
        return hits;
    }

    public boolean isSunk() {
        return this.sunk;
    }

    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public char getSymbol() {
        return this.symbol;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getStartCol() {
        return this.startCol;
    }
    
    public int getStartRow() {
        return this.startRow;
    }
    
    public Orientation getOrientation() {
        return this.orientation;
    }
    
    public void clearPosition() { //método a parte para não ter como trocar para true sem passar pelo setPosiiton()
        this.placed = false;
    }
    
    public boolean isPlaced() {
        return this.placed;
    }

    public abstract String getDescription();
}