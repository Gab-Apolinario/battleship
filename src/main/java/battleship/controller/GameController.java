package battleship.controller;

import java.util.List;
import battleship.model.*;

public class GameController {
    private GameState gameState;
    private HumanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private List<Ship> playerFleet;
    private Ship selectedShip;
    private Player winner;

    public GameController() {
        this.gameState = GameState.MENU;
        this.humanPlayer = null;
        this.aiPlayer = null;
        this.playerFleet = null;
        this.selectedShip = null;
        this.winner = null;
    }

    public void startGame() {
        
    }

    public void selectShip(Ship ship) {
        this.selectedShip = ship;
    }

    public boolean placeSelectedShip(int row, int col) {
        return false;
    }

    public boolean rotateSelectedShip() {
        return false;
    }

    public void randomizePlayerFleet() {

    }

    public boolean finishPreparation() {
        return false;
    }

    public ShotResult playerAttack(int row, int col) {
        return null;
    }

    public ShotResult enemyAttack() {
        return null;
    }

    public boolean isGameOver(Board hit) {
        return false;
    }

    public GameState getState() {
        return this.gameState;
    }

    public HumanPlayer getHuman() {
        return this.humanPlayer;
    }

    public AIPlayer getAI() {
        return this.aiPlayer;
    }

    public List<Ship> getPlayerFleet() {
        return this.playerFleet;
    }

    public Ship getSelectedShip() {
        return this.selectedShip;
    }

    public Player getWinner() {
        return this.winner;
    }
}