package battleship.view;

import battleship.controller.GameController;
import battleship.model.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) { //'stage' = the window open. The name can be changed (has a title, minimize/exit button)
        GameController gameController = new GameController();
        GameWindow gameWindow = new GameWindow(stage, gameController);
        stage.setTitle("Batalha Naval");
        gameWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
