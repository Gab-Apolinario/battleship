package battleship.controller;

import java.util.List;
import java.util.Random;

import battleship.model.*;

public class GameController {
    private GameState gameState;
    private HumanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private List<Ship> playerFleet;
    private Ship selectedShip;
    private Player winner;
    private static final int BOARD_SIZE = 10;

    public GameController() {
        this.gameState = GameState.MENU;
        this.humanPlayer = null;
        this.aiPlayer = null;
        this.playerFleet = null;
        this.selectedShip = null;
        this.winner = null;
    }

    public void startGame() {
        if (gameState == GameState.MENU || gameState == GameState.END_GAME) {
            //reseta variáveis
            this.selectedShip = null;
            this.winner = null;
            
            //PLAYER
            Board humanBoard = new Board(BOARD_SIZE);
            humanPlayer = new HumanPlayer("Player 1", humanBoard);
            playerFleet = createFleet();
            
            //if botão aleatório
            //humanPlayer.getBoard().placeFleetRandomly(playerFleet, new java.util.Random()); //TESTE

            //AI
            Board aiBoard = new Board(BOARD_SIZE);
            aiPlayer = new AIPlayer("AI", aiBoard);

            gameState = GameState.PREPARATION;
        }
    }

    private List<Ship> createFleet() {
        Carrier carrier = new Carrier(0, Orientation.HORIZONTAL);
        Battleship battleship = new Battleship(1, Orientation.HORIZONTAL);
        Cruiser cruiser = new Cruiser(2, Orientation.VERTICAL);
        Submarine submarine = new Submarine(3, Orientation.HORIZONTAL);
        Destroyer destroyer = new Destroyer(4, Orientation.VERTICAL);
        return List.of(carrier, battleship, cruiser, submarine, destroyer);
    }

    public void selectShip(Ship ship) {
        if (gameState == GameState.PREPARATION) {
            this.selectedShip = ship;
        }
    }

    public boolean placeSelectedShip(int row, int col) {
        if (gameState == GameState.PREPARATION && selectedShip != null) {
            return humanPlayer.getBoard().placeShip(selectedShip, row, col, selectedShip.getOrientation());
        }
        selectedShip = null;
        return false;
    }

    public boolean rotateSelectedShip() {
        if (gameState == GameState.PREPARATION && selectedShip != null) {
            if (selectedShip.isPlaced()) {
                return humanPlayer.getBoard().rotateShip(selectedShip);
            }
            
            //navio ainda na mão do jogador, apenas altera a orientação
            Orientation oldOrientation = selectedShip.getOrientation();
            Orientation newOrientation;
            if (oldOrientation == Orientation.HORIZONTAL) {
                newOrientation = Orientation.VERTICAL;
            } else {
                newOrientation = Orientation.HORIZONTAL;
            }
            selectedShip.setOrientation(newOrientation);
            return true;
        }
        return false;
    }

    public void randomizePlayerFleet() {
        if (gameState == GameState.PREPARATION) {
            Random random = new Random();
            humanPlayer.getBoard().placeFleetRandomly(playerFleet, random);
        }
    }

    public boolean finishPreparation() {
        if (gameState == GameState.PREPARATION) {
            //verifica se todos os navios foram posicionados
            for (int i = 0; i < playerFleet.size(); i++) {
                if (!playerFleet.get(i).isPlaced()) {
                    return false;
                }
            }

            //posiciona navios da IA
            List<Ship> aiFleet = createFleet();
            Random random = new Random();
            aiPlayer.getBoard().placeFleetRandomly(aiFleet, random);

            //trava os tabuleiros
            humanPlayer.getBoard().lock();
            aiPlayer.getBoard().lock();
            gameState = GameState.PLAYER_ATTACK;
            return true;
        }
        return false;
    }

    public ShotResult playerAttack(int row, int col) {
        if (gameState == GameState.PLAYER_ATTACK) {
            //fireAt
        }
        return null;
    }

    public ShotResult enemyAttack() {
        if (gameState == GameState.ENEMY_ATTACK) {
            //aiPlayer.chooseTarget(humanPlayer.getBoard());
        }
        return null;
    }

    private boolean isGameOver(Board hit) {
        return hit.allShipsSunk();
    }

    //region Getters
    public GameState getState() {
        return this.gameState;
    }

    public HumanPlayer getHuman() {
        return this.humanPlayer;
    }

    public AIPlayer getAi() {
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
    //endregion
}