package battleship.view;


import battleship.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PreparationScreen extends Screen {
    private Button startButton;
    private Button randomFleetButton;
    private VBox layout;
    private Label titleLabel;
    private BoardView boardView;
    
    public PreparationScreen(GameController gameController, GameWindow window) {
        super(gameController, window);
        startButton = new Button("START");
        randomFleetButton = new Button("ALEATÓRIO");

        layout = new VBox(10);
        titleLabel = new Label("PREPARATION");
        boardView = new BoardView(gameController.getHuman().getBoard(), false, false);

        layout.getChildren().addAll(titleLabel, boardView.getGrid(),randomFleetButton, startButton);
        layout.setAlignment(Pos.CENTER);
        startButton.setOnAction(e -> {
            if (gameController.finishPreparation()) {
                window.update();
            }
        });

        randomFleetButton.setOnAction(e -> {
            gameController.randomizePlayerFleet();
            boardView.refresh();
        });
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}
