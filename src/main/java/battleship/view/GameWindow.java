package battleship.view;

import battleship.controller.GameController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow {
    private Stage stage;
    private GameController gameController;
    private Screen currentScreen;

    public GameWindow(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
    }

    public void show() {
        update(); //verifica estado e cria a cena
        stage.show(); //mostra
    }

    public void update() {
        switch (gameController.getState()) {
            case MENU:
                currentScreen = new MenuScreen(gameController, this);
                break;
            case PREPARATION:
                currentScreen = new PlaceholderScreen(gameController, this, "PREPARATION");
                break;
            case PLAYER_ATTACK:
                //currentScreen = new PlayerAttackScreen(gameController, this);
                currentScreen = new PlaceholderScreen(gameController, this, "PLAYER_ATTACK");
                break;
            case ENEMY_ATTACK:
                //currentScreen = new EnemyAttackScreen(gameController, this);
                currentScreen = new PlaceholderScreen(gameController, this, "ENEMY_ATTACK");
                break;
            case END_GAME:
                //currentScreen = new EndGameScreen(gameController, this);
                currentScreen = new PlaceholderScreen(gameController, this, "END_GAME");
                break;
        }
        stage.setScene(new Scene(currentScreen.getRoot(), 600, 600));
    }
}
