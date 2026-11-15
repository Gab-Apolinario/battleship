package battleship.view;

import battleship.controller.GameController;
import battleship.model.Ship;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PreparationScreen extends Screen {
    private Button startButton;
    private Button randomFleetButton;
    private VBox layout;
    private Label titleLabel;
    private Label infoLabel;
    private BoardView boardView;
    private VBox titleBox; //container destacado do título
    private VBox fleetCard; //card informativo da frota
    private VBox helpCard; //card com as instruções
    private VBox sidePanel;
    private HBox contentBox; //tabuleiro à esquerda e coluna à direita
    private HBox buttonsBox;

    public PreparationScreen(GameController gameController, GameWindow window) {
        super(gameController, window);
        startButton = new Button("INICIAR BATALHA");
        randomFleetButton = new Button("SORTEAR POSIÇÕES");

        layout = new VBox(5);
        titleLabel = new Label("PREPARAÇÃO");
        titleLabel.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 42px; -fx-font-weight: bold;");
        infoLabel = new Label("Posicione sua frota e inicie a batalha");
        infoLabel.setStyle("-fx-text-fill: #9fb3cf; -fx-font-size: 18px;");
        boardView = new BoardView(gameController.getHuman().getBoard(), false, true, 54);

        titleBox = new VBox(10);
        titleBox.getChildren().addAll(titleLabel, infoLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 25, 20, 25)); //espaço interno
        titleBox.setMaxWidth(1100); //impede de esticar até a largura da janela
        titleBox.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12; -fx-border-color: #b89d12; -fx-border-radius: 12; -fx-border-width: 2;"); //fundo, cantos e borda dourada

        fleetCard = createCard("SUA FROTA");
        fleetCard.getChildren().addAll(createFleetRow("Porta-aviões", 5), createFleetRow("Encouraçado", 4), createFleetRow("Cruzador", 3), createFleetRow("Submarino", 3), createFleetRow("Destroyer", 2)); //uma linha por navio, com nome e quadradinhos
        helpCard = createCard("COMO POSICIONAR", "•  Clique em um navio para selecioná-lo", "•  Clique nele de novo para girar", "•  Clique em uma célula vazia para movê-lo", "•  Borda verde: posição válida", "•  Borda vermelha: posição inválida"); //instruções com marcadores

        randomFleetButton.setPrefSize(320, 60);
        randomFleetButton.setStyle("-fx-background-color: #0f3a4a; -fx-text-fill: #cfcece; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #9fb3cf; -fx-border-radius: 6; -fx-background-radius: 6;"); //cor e fonte do botão
        startButton.setPrefSize(320, 60);
        startButton.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: #cfcece; -fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 6;"); //cor e fonte do botão

        sidePanel = new VBox(15);
        sidePanel.getChildren().addAll(fleetCard, helpCard);
        sidePanel.setAlignment(Pos.CENTER);
        sidePanel.setPrefWidth(380); //largura fixa da coluna

        contentBox = new HBox(50);
        contentBox.getChildren().addAll(boardView.getGrid(), sidePanel);
        contentBox.setAlignment(Pos.CENTER);
        VBox.setMargin(contentBox, new Insets(20, 0, 0, 0));

        buttonsBox = new HBox(30);
        buttonsBox.getChildren().addAll(randomFleetButton, startButton);
        buttonsBox.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonsBox, new Insets(15, 0, 0, 0));

        layout.getChildren().addAll(titleBox, contentBox, buttonsBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #002932;");
        startButton.setOnAction(e -> {
            if (gameController.finishPreparation()) {
                window.update();
            }
        });

        boardView.setListener((r, c) -> handleCellClick(r, c));

        randomFleetButton.setOnAction(e -> {
            gameController.randomizePlayerFleet();
            boardView.setSelectedShip(gameController.getSelectedShip());
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

        boardView.setSelectedShip(gameController.getSelectedShip());
        boardView.refresh(); //sempre atualiza o tabuleiro (visualmente)
    }

    //cria um card com título dourado e várias linhas de texto
    private VBox createCard(String title, String... lines) {
        VBox card = new VBox(8);
        Label cardTitle = new Label(title);
        cardTitle.setStyle("-fx-text-fill: #b89d12; -fx-font-size: 22px; -fx-font-weight: bold;");
        card.getChildren().add(cardTitle);
        for (String line : lines) {
            Label lineLabel = new Label(line);
            lineLabel.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 17px;");
            lineLabel.setWrapText(true);
            card.getChildren().add(lineLabel);
        }
        card.setPadding(new Insets(15, 20, 15, 20));
        card.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12;");
        return card;
    }

    //cria uma linha da frota: nome à esquerda e quadradinhos à direita
    private HBox createFleetRow(String name, int size) {
        HBox row = new HBox(10);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 17px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); //o espaço vazio ocupa o que sobrar e empurra os quadradinhos para a direita
        HBox squares = new HBox(4);
        for (int i = 0; i < size; i++) {
            Region square = new Region();
            square.setMinSize(22, 22);
            square.setMaxSize(22, 22);
            square.setStyle("-fx-background-color: #6c6c6c; -fx-background-radius: 4;");
            squares.getChildren().add(square);
        }
        squares.setAlignment(Pos.CENTER_RIGHT);
        row.getChildren().addAll(nameLabel, spacer, squares);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}