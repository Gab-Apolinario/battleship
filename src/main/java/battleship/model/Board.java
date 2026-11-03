package battleship.model;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private int size;
    private CellState[][] grid;
    private List<Ship> ships;
    private Map<String, Ship> shipByCoordinate;
    private int shotsReceived;
    private int hitsReceived;
    private int missesReceived;
    private int sunkShips;
    private boolean locked;
    private String lastShot;

    public Board(int size) {
        this.size = size;

        // Cria o grid
        grid = new CellState[size][size];
        
        //coloca WATER em todas as posições
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = CellState.WATER;
            }
        }

        this.ships = new ArrayList<>();
        this.shipByCoordinate = new HashMap<>();
        this.shotsReceived = 0;
        this.hitsReceived = 0;
        this.missesReceived = 0;
        this.sunkShips = 0;
        this.locked = false;
        this.lastShot = "";
    }

    public boolean placeShip(Ship ship, int row, int col, Orientation orientation) {
        return false;
    }

    public boolean rotateShip(Ship ship) {
        return false;
    }

    public void removeShip(Ship ship) {

    }

    public boolean canPlace(Ship ship, int row, int col, Orientation orientation) {
        return false;
    }

    public ShotResult receiveShot(int row, int col) {
        return null;
    }

    //devolve TRUE se a coordenada existe no tabuleiro
    public boolean isInside(int row, int col) {
        if ((row >= 0 && row <= size - 1) && (col >= 0 && col <= size - 1)) {
            return true;
        }
        return false;
    }

    //devolve valor do enum CellState da posição (row,col)
    public CellState getCellState(int row, int col) {
        if (!isInside(row, col)) { //verificação para evitar o erro antes dele acontecer
            return null; //não quebra
        } else {
            return grid[row][col];
        }
    }

    //devolve navio da célula ou null se for água
    public Ship getShipAt(int row, int col) {
        return shipByCoordinate.get(coordinateKey(row, col));
    }

    public void placeFleetRandomly(List<Ship> fleet, Random random) {
        
    }

    public void lock() {

    }

    public boolean allShipsSunk() {
        return false;
    }

    public int getRemainingShips() {
        return 0;
    }

    //Formata posição para 'A2', 'D6', etc
    public static String coordinateKey(int row, int col) {
        char letra = (char)('A' + col);
        return "" + letra + (row + 1);
    }
}