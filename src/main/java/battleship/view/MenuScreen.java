package battleship.view;

import battleship.controller.GameController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;

public class MenuScreen extends Screen{
    private Button playButton;
    private VBox layout;
    private Label titleLabel;

    public MenuScreen(GameController gameController, GameWindow window) {
        super(gameController, window);
        playButton = new Button("JOGAR");
        playButton.setPrefSize(360, 80);
        playButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: #cfcece; -fx-font-size: 30px; -fx-font-weight: bold; -fx-background-radius: 8;"); //cor e fonte do botão
        layout = new VBox(10);
        titleLabel = new Label("BATALHA NAVAL");
        titleLabel.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 100px; " + TITLE_FONT);
        VBox.setMargin(playButton, new Insets(50, 0, 0, 0));
        layout.getChildren().addAll(titleLabel, playButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #002932; " + BODY_FONT);
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