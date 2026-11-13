package battleship.view;

import battleship.controller.GameController;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Parent;

public class PlaceholderScreen extends Screen {
    private VBox layout;
    private Label titleLabel;
    
    public PlaceholderScreen(GameController gameController, GameWindow window, String title) {
        super(gameController, window);
        this.titleLabel = new Label(title);
        layout = new VBox(10);
        layout.getChildren().add(titleLabel);
        layout.setAlignment(Pos.CENTER);
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}
