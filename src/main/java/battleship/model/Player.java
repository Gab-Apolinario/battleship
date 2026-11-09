package battleship.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    private String name;
    protected Board board;
    private int shotsFired;
    private int hits;
    private int misses;
    private int sunkEnemyShips;
    private int turnsPlayed;
    private int score;
    private boolean ready;
    private String lastShot;
    protected Set<String> shotHistory;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.shotsFired = 0;
        this.hits = 0;
        this.misses = 0;
        this.sunkEnemyShips = 0;
        this.turnsPlayed = 0;
        this.score = 0;
        this.ready = false;
        this.lastShot = "";
        this.shotHistory = new HashSet<>();
    }

    public ShotResult fireAt(Board opponent, int row, int col) {
        if (opponent == null || !opponent.isInside(row, col) || alreadyShot(row, col)) {
            return ShotResult.INVALID; //retorna INVALID se o oponente for nulo, coordenadas inválidas ou já atiradas
        }

        ShotResult result = opponent.receiveShot(row, col);
        registerResult(row, col, result);
        return result;
    }

    public void registerResult(int row, int col, ShotResult result) {
        if (result == ShotResult.INVALID) {
            return; //não registra resultados inválidos
        }

        switch (result) {
            case HIT:
                hits++;
                addScore(10); //adiciona 10 pontos por acerto
                break;
            case MISS:
                misses++;
                break;
            case SUNK:
                hits++;
                sunkEnemyShips++;
                addScore(50); //adiciona 50 pontos por afundar um navio
                break;
        }

        shotsFired++;
        turnsPlayed++;
        lastShot = Board.coordinateKey(row, col);
        shotHistory.add(lastShot);
    }

    public boolean alreadyShot(int row, int col) {
        return shotHistory.contains(Board.coordinateKey(row, col));
    }

    public void addScore(int points) {
        score += points;
    }

    public void setBoard(Board board) {
        if (board == null) {
            return;
        }

        this.board = board;
        resetStats();
    }

    public double getAccuracy() {
        if (shotsFired == 0) { //evita divisão por zero
            return 0;
        }

        return (double) hits / shotsFired; //converte antes de dividir para ter decimal
    }

    private void resetStats() {
        shotsFired = 0;
        hits = 0;
        misses = 0;
        sunkEnemyShips = 0;
        turnsPlayed = 0;
        score = 0;
        shotHistory.clear();
        lastShot = "";
        ready = false;
    }

    public int getScore() {
        return this.score;
    }

    public abstract boolean isAutomatic();
}