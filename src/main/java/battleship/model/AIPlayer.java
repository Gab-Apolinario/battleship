package battleship.model;

import java.util.Random;
import java.util.ArrayDeque;
import java.util.Deque;

public class AIPlayer extends Player {
    private Random random;
    private Deque<String> targetQueue; //fila de coordenadas-alvo para disparos futuros
    private int lastHitRow; //linha do último acerto
    private int lastHitCol; //coluna do último acerto
    private boolean huntMode; //caça = achou uma parte de navio

    public AIPlayer(String name, Board board) {
        super(name, board);
        this.random = new Random();
        this.targetQueue = new ArrayDeque<>();
        this.lastHitRow = -1; //ainda não teve acerto, primeira jogada
        this.lastHitCol = -1;
        this.huntMode = false;
    }

    public String chooseTarget(Board opponent) {
        //laço que repete até achar uma célula válida para atirar
        //random.nextInt(0, opponent.getSize()) -> usa tamanho do board, garantindo que está dentro do tabuleiro
        return null;
    }

    public void enqueueNeighbors(int row, int col, int boardSize) {
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            //4 candidados: cima (row - 1, col), baixo (row + 1, col), esquerda (row, col - 1), direita (row, col + 1)
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];

            //valida se está dentro do tabuleiro e se já foi atirada antes de adicionar à fila 
            if ((newRow >= 0 && newRow <= boardSize - 1) && (newCol >= 0 && newCol <= boardSize - 1) && !alreadyShot(newRow, newCol)) { //isInside() do Board
                targetQueue.add(newRow + "," + newCol);
            }
        }
    }

    @Override
    public void registerResult(int row, int col, ShotResult result) {
        super.registerResult(row, col, result);

        switch (result) {
            case HIT:
                huntMode = true;
                lastHitRow = row;
                lastHitCol = col;
                enqueueNeighbors(row, col, board.getSize());
                break;
            case MISS:
                if (targetQueue.isEmpty()){
                    resetTargetMode();
                }
                break;
            case SUNK:
                resetTargetMode();
                break;    
        }
    }

    public void resetTargetMode() {
        //se SUNK, limpa fila, reseta lastHitRow e lastHitCol e huntMode -> false
        targetQueue.clear();
        lastHitRow = -1;
        lastHitCol = -1;
        huntMode = false;
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }
}