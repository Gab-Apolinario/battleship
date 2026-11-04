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
        //tabuleiro travado OU já está posicionado OU navio NÃO cabe
        if (locked || ship.isPlaced() || !canPlace(ship, row, col, orientation)) {
            return false;
        }

        //Navio CABE
        ship.setPosition(row, col, orientation); //registra posição do navio em Ship
        
        //grid altera CellStates para SHIP
        for (int i = 0; i < ship.getSize(); i++) {
            int linha;
            int coluna;

            if (orientation == Orientation.HORIZONTAL) {
                linha = row;
                coluna = col + i;
            } else {
                linha = row + i;
                coluna = col;
            }

            grid[linha][coluna] = CellState.SHIP;
            shipByCoordinate.put(coordinateKey(linha, coluna), ship); //registra navio novo no MAP
        }

        ships.add(ship); //adiciona na lista ships

        return true;
    }

    public boolean rotateShip(Ship ship) {
        if (locked || !ship.isPlaced()) { //tabuleiro travado OU navio não está posicionado
            return false;
        }
        
        //girar = removeShip -> setPosition com orientation inversa
        //guarda posição atual
        int oldRow = ship.getStartRow();
        int oldCol = ship.getStartCol();
        Orientation oldOrientation = ship.getOrientation();
        
        Orientation newOrientation;
        if (oldOrientation == Orientation.HORIZONTAL) {
            newOrientation = Orientation.VERTICAL;
        } else {
            newOrientation = Orientation.HORIZONTAL;
        }

        removeShip(ship); //limpa posição

        if (canPlace(ship, oldRow, oldCol, newOrientation)) { //se puder posicionar, posiciona na posição rotacionada
            placeShip(ship, oldRow, oldCol, newOrientation);
            return true;
        } else {
            placeShip(ship, oldRow, oldCol, oldOrientation); //senão, volta na posição original
            return false;
        }
    }

    public void removeShip(Ship ship) {
        if (locked || !ship.isPlaced()) { //tabuleiro travado OU navio não está posicionado
            return;
        }

        //grid altera CellStates para WATER
        for (int i = 0; i < ship.getSize(); i++) {
            int linha;
            int coluna;

            if (ship.getOrientation() == Orientation.HORIZONTAL) {
                linha = ship.getStartRow();
                coluna = ship.getStartCol() + i;
            } else {
                linha = ship.getStartRow() + i;
                coluna = ship.getStartCol();
            }

            grid[linha][coluna] = CellState.WATER;
            shipByCoordinate.remove(coordinateKey(linha, coluna), ship); //remove navio do MAP
        }
        
        ship.clearPosition(); //placed = false
        ships.remove(ship); //remove da lista
    }

    //responde 'navio cabe nessa posição, com essa orientação?' -> bool
    public boolean canPlace(Ship ship, int row, int col, Orientation orientation) {
        //for com as chamadas
        //return / break caso ache falha
        
        for (int i = 0; i < ship.getSize(); i++) {
            int linha;
            int coluna;
            
            //ajusta var locais para verificar orientação, assim como foi feito em segmentIndexAt() (Ship)
            if (orientation == Orientation.HORIZONTAL) {
                linha = row; //H -> trava row
                coluna = col + i; //H -> muda col
            } else {
                linha = row + i; //V -> muda row
                coluna = col; //V -> trava col
            }
            
            //pergunta se a célula do navio está fora do grid
            if (!isInside(linha, coluna)) {
                return false;
            }
            
            //pergunta se já tem um navio na posição
            if (getShipAt(linha, coluna) != null) {
                return false;
            }
        }
        return true; //pode posicionar (depois de todas as verificações)
    }

    //Valida tiro recebido, devolve enum ShotResult
    public ShotResult receiveShot(int row, int col) {
        CellState cellState = getCellState(row, col);

        //Se não está no tabuleiro OU É SHIP_HIT OU É WATER_HIT 
        if ((!isInside(row, col)) || (cellState == CellState.SHIP_HIT) || (cellState == CellState.WATER_HIT)) {
            return ShotResult.INVALID;
        }

        //MISS
        if (cellState == CellState.WATER) {
            shotsReceived++;
            missesReceived++;
            lastShot = coordinateKey(row, col); //guarda a última posição atingida

            grid[row][col] = CellState.WATER_HIT; //marca a célula como água atingida
            return ShotResult.MISS;
        }

        //HIT
        if (cellState == CellState.SHIP) {
            Ship hitShip = getShipAt(row, col);
            int index = hitShip.segmentIndexAt(row, col);
            hitShip.registerHit(index);
            shotsReceived++;
            hitsReceived++;
            lastShot = coordinateKey(row, col);
            grid[row][col] = CellState.SHIP_HIT;

            if (hitShip.isSunk()) {
                sunkShips++;
                return ShotResult.SUNK;
            }

            return ShotResult.HIT;
        }

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
        this.locked = true;
    }

    public boolean allShipsSunk() {
        //se a lista de navios não está vazia (começo do jogo) E todos os navios foram afundados
        if (!ships.isEmpty() && sunkShips == ships.size()) {
            return true;
        }
        return false;
    }

    public int getRemainingShips() {
        return ships.size() - sunkShips;
    }

    //Formata posição para 'A2', 'D6', etc
    public static String coordinateKey(int row, int col) {
        char letra = (char)('A' + col);
        return "" + letra + (row + 1);
    }
}