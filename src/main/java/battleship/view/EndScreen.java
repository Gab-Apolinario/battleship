package battleship.view;

import battleship.controller.GameController;
import battleship.model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EndScreen extends Screen {
    private Label winnerName;
    private Button playAgainButton;
    private VBox layout;
    private VBox winnerBox;
    private HBox columnsBox;

    public EndScreen(GameController gameController, GameWindow gamesWindow) {
        super(gameController, gamesWindow);
        winnerName = new Label("VENCEDOR: " + gameController.getWinner().getName());
        winnerName.setStyle("-fx-text-fill: #b89d12; -fx-font-size: 60px; " + TITLE_FONT);

        winnerBox = new VBox();
        winnerBox.getChildren().add(winnerName);
        winnerBox.setAlignment(Pos.CENTER);
        winnerBox.setPadding(new Insets(20, 25, 20, 25));
        winnerBox.setMaxWidth(1100);
        winnerBox.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12; -fx-border-color: #b89d12; -fx-border-radius: 12; -fx-border-width: 2;");

        columnsBox = new HBox(40);
        columnsBox.getChildren().addAll(createColumn(gameController.getHuman()), createColumn(gameController.getAi())); //coluna do jogador e coluna do computador
        columnsBox.setAlignment(Pos.CENTER);
        VBox.setMargin(columnsBox, new Insets(20, 0, 0, 0));

        playAgainButton = new Button("JOGAR NOVAMENTE");
        playAgainButton.setPrefSize(360, 70);
        playAgainButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: #cfcece; -fx-font-size: 26px; -fx-font-weight: bold; -fx-background-radius: 8;");
        VBox.setMargin(playAgainButton, new Insets(20, 0, 0, 0));
        layout = new VBox(10);
        layout.getChildren().addAll(winnerBox, columnsBox, playAgainButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #002932; " + BODY_FONT);
        playAgainButton.setOnAction(e -> { //ao clicar no botão:
            gameController.startGame(); //reset o jogo
            window.update(); //troca a tela
        });
    }

    //cria a coluna de um jogador: nome, stats e tabuleiro pequeno
    private VBox createColumn(Player player) {
        VBox column = new VBox(8);
        Label nameLabel = new Label(player.getName());
        nameLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 28px; " + TITLE_FONT);
        column.getChildren().addAll(nameLabel,
            createStatRow("Tiros disparados", String.valueOf(player.getShotsFired())),
            createStatRow("Acertos", String.valueOf(player.getHits())),
            createStatRow("Erros", String.valueOf(player.getMisses())),
            createStatRow("Navios afundados", String.valueOf(player.getSunkEnemyShips())),
            createStatRow("Jogadas", String.valueOf(player.getTurnsPlayed())),
            createStatRow("Pontuação", String.valueOf(player.getScore())),
            createStatRow("Taxa de acertos", String.format("%.1f%%", player.getAccuracy() * 100)));
        BoardView smallBoard = new BoardView(player.getBoard(), false, false, 28); //tabuleiro pequeno, sem clique
        smallBoard.getGrid().setAlignment(Pos.CENTER); //centraliza o tabuleiro dentro da coluna
        VBox.setMargin(smallBoard.getGrid(), new Insets(10, 0, 0, 0)); //espaço externo acima do tabuleiro
        column.getChildren().add(smallBoard.getGrid());
        column.setAlignment(Pos.TOP_CENTER);
        column.setPadding(new Insets(15, 20, 15, 20));
        column.setPrefWidth(480); //largura fixa da coluna
        if (player == gameController.getWinner()) {
            column.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12; -fx-border-color: #b89d12; -fx-border-radius: 12; -fx-border-width: 2;"); //vencedor ganha borda dourada
        } else {
            column.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12;");
        }
        return column;
    }

    //cria uma linha de stat: nome à esquerda e valor à direita
    private HBox createStatRow(String label, String value) {
        HBox row = new HBox(10);
        Label labelText = new Label(label);
        labelText.setStyle("-fx-text-fill: #9fb3cf; -fx-font-size: 18px;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label valueText = new Label(value);
        valueText.setStyle("-fx-text-fill: #ececec; -fx-font-size: 18px; -fx-font-weight: bold;");
        row.getChildren().addAll(labelText, spacer, valueText);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}