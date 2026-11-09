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
        //valida se está dentro do tabuleiro antes de adicionar à fila
        //4 candidados: cima (row - 1, col), baixo (row + 1, col), esquerda (row, col - 1), direita (row, col + 1)
    }

    public void resetTargetMode() {
        //se SUNK, limpa fila, reseta lastHitRow e lastHitCol e huntMode -> false
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }
}