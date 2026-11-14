package battleship.view;

import battleship.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndScreen extends Screen {
    private Label winnerName;
    private Label winnerStatus;
    private Button playAgainButton;
    private VBox layout;

    public EndScreen(GameController gameController, GameWindow gamesWindow) {
        super(gameController, gamesWindow);
        winnerName = new Label(gameController.getWinner().getName()); //label nome
        winnerStatus = new Label(statusText(gameController.getWinner())); //label status
        playAgainButton = new Button("JOGAR NOVAMENTE");
        layout = new VBox(10);
        layout.getChildren().addAll(winnerName, winnerStatus, playAgainButton);
        layout.setAlignment(Pos.CENTER);
        playAgainButton.setOnAction(e -> { //ao clicar no botão:
            gameController.startGame(); //reset o jogo
            window.update(); //troca a tela
        });
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}