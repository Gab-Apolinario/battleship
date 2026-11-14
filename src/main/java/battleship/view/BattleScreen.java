package battleship.view;

import battleship.controller.GameController;
import battleship.controller.GameState;
import battleship.model.ShotResult;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BattleScreen extends Screen {
    private BoardView enemyView;
    private BoardView playerView;
    private Label playerName;
    private Label enemyName;
    private Label playerStatusLabel;
    private Label enemyStatusLabel;
    private HBox layout;
    private VBox vBoxPlayer;
    private VBox vBoxEnemy;

    public BattleScreen(GameController gameController, GameWindow gameWindow) {
        super(gameController, gameWindow);
        //LAYOUT TELA
        enemyView = new BoardView(gameController.getAi().getBoard(), true, true); //grid inimigo
        enemyView.setListener((r, c) -> handlePlayerShot(r, c)); //'quando clicarem em (r, c), rode o handlePlayerShot(r, c)'
        playerView = new BoardView(gameController.getHuman().getBoard(), false, false); //grid Human Player
        playerName = new Label(gameController.getHuman().getName());
        enemyName = new Label(gameController.getAi().getName());
        playerStatusLabel = new Label(statusText(gameController.getHuman()));
        enemyStatusLabel = new Label(statusText(gameController.getAi()));
        vBoxPlayer = new VBox(10);
        vBoxEnemy = new VBox(10);
        vBoxPlayer.getChildren().addAll(playerName, playerStatusLabel, playerView.getGrid());
        vBoxEnemy.getChildren().addAll(enemyName, enemyStatusLabel, enemyView.getGrid());
        vBoxPlayer.setAlignment(Pos.CENTER);
        vBoxEnemy.setAlignment(Pos.CENTER);
        
        layout = new HBox(10);
        layout.getChildren().addAll(vBoxPlayer, vBoxEnemy);
        layout.setAlignment(Pos.CENTER);
    }

    @Override
    public Parent getRoot() {
        return layout;
    } 

    private void handlePlayerShot(int row, int col) {
        ShotResult playerShot = gameController.playerAttack(row, col); //atira
        if (playerShot == ShotResult.INVALID) {
            return;
        }

        enemyView.refresh(); //atualiza grid do inimigo
        refreshStatus(); //atualiza status
        if (gameController.getState() == GameState.END_GAME) {
            window.update();
        } else if (gameController.getState() == GameState.ENEMY_ATTACK) { //troca de turno
            playEnemyTurn();
        }
    }

    private void playEnemyTurn() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.8)); //cria pausa para não realizar ataque instantaneo
        pause.setOnFinished(e -> {
            gameController.enemyAttack(); //ataca
            playerView.refresh(); //atualiza grid
            refreshStatus(); //atualiza status

            if (gameController.getState() == GameState.END_GAME) {
                window.update();
            } else if (gameController.getState() == GameState.ENEMY_ATTACK) { //repete a jogada
                playEnemyTurn();
            }
        });

        pause.play();
    }

    private void refreshStatus() {
        playerStatusLabel.setText(statusText(gameController.getHuman()));
        enemyStatusLabel.setText(statusText(gameController.getAi()));
    }

}