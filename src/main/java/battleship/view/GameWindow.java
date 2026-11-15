package battleship.view;

import battleship.controller.GameController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow {
    private Stage stage;
    private Scene scene;
    private GameController gameController;
    private Screen currentScreen;

    public GameWindow(Stage stage, GameController gameController) {
        this.stage = stage;
        this.scene = null;
        this.gameController = gameController;
    }

    public void show() {
        update(); //verifica estado e cria a cena
        stage.setMaximized(true); //coloca a tela em forma Maximizada
        stage.show(); //mostra
    }

    //Sistema de troca de tela
    public void update() {
        switch (gameController.getState()) { //verifica o GameState atual e mostra a tela pertinente ao GameState
            case MENU:
                currentScreen = new MenuScreen(gameController, this);
                break;
            case PREPARATION:
                currentScreen = new PreparationScreen(gameController, this);
                break;
            case PLAYER_ATTACK:
                currentScreen = new BattleScreen(gameController, this);
                break;
            case ENEMY_ATTACK:
                currentScreen = new BattleScreen(gameController, this);
                break;
            case END_GAME:
                currentScreen = new EndScreen(gameController, this);
                break;
        }

        //SEGURANÇA
        if (scene == null) { //caso não tenha Scene criada, cria
            scene = new Scene(currentScreen.getRoot());
            stage.setScene(scene);
        } else {
            scene.setRoot(currentScreen.getRoot()); //caso tenha Scene criada, usa ela
        }
    }
}
