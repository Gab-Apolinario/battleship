package battleship.view;

import battleship.controller.GameController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Parent;

public class MenuScreen extends Screen{
    private Button playButton;
    private VBox layout;
    private Label titleLabel;
    
    public MenuScreen(GameController gameController, GameWindow window) {
        super(gameController, window);
        playButton = new Button("Play");
        layout = new VBox(10);
        titleLabel = new Label("MENU");
        layout.getChildren().addAll(titleLabel, playButton);
        layout.setAlignment(Pos.CENTER);
        playButton.setOnAction(e -> {
            gameController.startGame();
            window.update();
        });
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}
