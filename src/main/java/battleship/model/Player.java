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
        return null;
    }

    public void registerResult(int row, int col, ShotResult result) {

    }

    public boolean alreadyShot(int row, int col) {
        return false;
    }

    public void addScore(int points) {

    }

    public void setBoard(Board board) {

    }

    public double getAccuracy() {
        return 0;
    }

    public abstract boolean isAutomatic();
}