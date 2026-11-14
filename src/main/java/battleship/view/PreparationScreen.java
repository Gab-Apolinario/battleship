package battleship.view;

import battleship.controller.GameController;
import battleship.model.Ship;
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
        boardView = new BoardView(gameController.getHuman().getBoard(), false, true);

        layout.getChildren().addAll(titleLabel, boardView.getGrid(),randomFleetButton, startButton);
        layout.setAlignment(Pos.CENTER);
        startButton.setOnAction(e -> {
            if (gameController.finishPreparation()) {
                window.update();
            }
        });

        boardView.setListener((r, c) -> handleCellClick(r, c));

        randomFleetButton.setOnAction(e -> {
            gameController.randomizePlayerFleet();
            boardView.refresh();
        });
    }

    private void handleCellClick(int row, int col) {
        Ship cellShip = gameController.getHuman().getBoard().getShipAt(row, col);
        
        if (cellShip != null) { //SE FOR NAVIO NA CELULA
            if (cellShip == gameController.getSelectedShip()) { //Se o navio nessa célula é igual ao selecionado
                gameController.rotateSelectedShip(); //rotaciona ao clicar
            } else {
                gameController.selectShip(cellShip); //seleciona o navio
            }
        } else {
            if (gameController.getSelectedShip() != null) { //se célula for AGUA e tem navio selecionado
                gameController.moveSelectedShip(row, col); //tenta mover para essa célula
            }
        }
        
        boardView.refresh(); //sempre atualiza o tabuleiro (visualmente)
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}
