package battleship.view;

import battleship.controller.GameController;
import battleship.controller.GameState;
import battleship.model.ShotResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BattleScreen extends Screen {
    private BoardView enemyView;
    private BoardView playerView;
    private Label playerName;
    private Label enemyName;
    private Label playerStatusLabel;
    private Label enemyStatusLabel;
    private Label turnStatusLabel;
    private Label infoLabel;
    private VBox turnBox; //container destacado do turno
    private VBox layout;
    private HBox hBoxViews;
    private VBox vBoxPlayer;
    private VBox vBoxEnemy;
    private Region viewsRegion;
    private HBox hBoxSubtitles;

    public BattleScreen(GameController gameController, GameWindow gameWindow) {
        super(gameController, gameWindow);
        //LAYOUT TELA
        enemyView = new BoardView(gameController.getAi().getBoard(), true, true, 42); //grid inimigo
        enemyView.setListener((r, c) -> handlePlayerShot(r, c)); //'quando clicarem em (r, c), rode o handlePlayerShot(r, c)'
        playerView = new BoardView(gameController.getHuman().getBoard(), false, false, 42); //grid Human Player

        playerName = new Label(gameController.getHuman().getName());
        playerName.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 34px; " + TITLE_FONT);
        enemyName = new Label(gameController.getAi().getName());
        enemyName.setStyle("-fx-text-fill: #cfcece; -fx-font-size: 34px; " + TITLE_FONT);

        playerStatusLabel = new Label();
        playerStatusLabel.setStyle("-fx-text-fill: #b9b9b9; -fx-font-size: 18px; -fx-font-weight: bold;");
        enemyStatusLabel = new Label();
        enemyStatusLabel.setStyle("-fx-text-fill: #b9b9b9; -fx-font-size: 18px; -fx-font-weight: bold;");

        vBoxPlayer = new VBox(10);
        vBoxEnemy = new VBox(10);
        vBoxPlayer.getChildren().addAll(playerName, playerStatusLabel, playerView.getGrid());
        vBoxEnemy.getChildren().addAll(enemyName, enemyStatusLabel, enemyView.getGrid());
        vBoxPlayer.setAlignment(Pos.CENTER);
        vBoxEnemy.setAlignment(Pos.CENTER);

        viewsRegion = new Region();
        viewsRegion.setPrefWidth(4);
        viewsRegion.setStyle("-fx-background-color: #2a4a66;");

        hBoxViews = new HBox(30);
        hBoxViews.getChildren().addAll(vBoxPlayer, viewsRegion, vBoxEnemy);
        hBoxViews.setAlignment(Pos.CENTER);
        turnStatusLabel = new Label("SUA VEZ");
        turnStatusLabel.setStyle("-fx-text-fill: #b89d12; -fx-font-size: 55px; " + TITLE_FONT);
        infoLabel = new Label("Clique em uma célula do tabuleiro inimigo para atirar");
        infoLabel.setStyle("-fx-text-fill: #9fb3cf; -fx-font-size: 22px; " + BODY_FONT);

        turnBox = new VBox(5); //cria o container com espaço de 5 entre os dois labels
        turnBox.getChildren().addAll(turnStatusLabel, infoLabel); //coloca os dois labels dentro do container
        turnBox.setAlignment(Pos.CENTER); //centraliza o conteúdo do container
        turnBox.setPadding(new Insets(12, 25, 12, 25)); //espaço interno entre a borda e o conteúdo
        turnBox.setMaxWidth(1250); //impede o container de esticar até a largura da janela
        turnBox.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12; -fx-border-color: #b89d12; -fx-border-radius: 12; -fx-border-width: 2;"); //fundo, cantos arredondados e borda dourada

        hBoxSubtitles = new HBox(25);
        hBoxSubtitles.getChildren().addAll(createLegendItem("#4582d2", "Água"), createLegendItem("#6c6c6c", "Seu navio"),
        createLegendItem("#1f3f68", "Tiro na Água"), createLegendItem("#5b0000", "Acerto"), createLegendItem("#b32828", "Navio Afundado"));
        hBoxSubtitles.setAlignment(Pos.CENTER);
        hBoxSubtitles.setPadding(new Insets(12, 25, 12, 25)); //espaço interno da legenda
        hBoxSubtitles.setMaxWidth(1250); //impede a legenda de esticar até a largura da janela
        hBoxSubtitles.setStyle("-fx-background-color: #0f3a4a; -fx-background-radius: 12;"); //fundo e cantos arredondados da legenda
        VBox.setMargin(hBoxSubtitles, new Insets(15, 0, 0, 0)); //espaço externo acima da legenda, afastando dos tabuleiros

        layout = new VBox(20);
        layout.getChildren().addAll(turnBox, hBoxViews, hBoxSubtitles);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #002932; " + BODY_FONT);

        refreshStatus();
    }

    @Override
    public Parent getRoot() {
        return layout;
    }

    private void handlePlayerShot(int row, int col) {
        ShotResult playerShot = gameController.playerAttack(row, col); //atira
        if (playerShot == ShotResult.INVALID) {
            return;
        }

        enemyView.refresh(); //atualiza grid do inimigo
        refreshStatus(); //atualiza status
        if (gameController.getState() == GameState.END_GAME) {
            window.update();
        } else if (gameController.getState() == GameState.ENEMY_ATTACK) { //troca de turno
            playEnemyTurn();
        }
    }

    private void playEnemyTurn() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.8)); //cria pausa para não realizar ataque instantaneo
        pause.setOnFinished(e -> {
            gameController.enemyAttack(); //ataca
            playerView.refresh(); //atualiza grid
            refreshStatus(); //atualiza status

            if (gameController.getState() == GameState.END_GAME) {
                window.update();
            } else if (gameController.getState() == GameState.ENEMY_ATTACK) { //repete a jogada
                playEnemyTurn();
            }
        });

        pause.play();
    }

    private void refreshStatus() {
        playerStatusLabel.setText(battleStatusText(gameController.getHuman()));
        enemyStatusLabel.setText(battleStatusText(gameController.getAi()));

        switch (gameController.getState()) {
            case PLAYER_ATTACK:
                turnStatusLabel.setText("SUA VEZ");
                infoLabel.setText("Clique em uma célula do tabuleiro inimigo para atirar");
                break;
            case ENEMY_ATTACK:
                turnStatusLabel.setText("VEZ DO COMPUTADOR");
                infoLabel.setText("Aguarde a jogada");
                break;
            default:
                break;
        }
    }

    private HBox createLegendItem(String color, String text) {
        HBox hBox = new HBox(10);
        Region region = new Region();
        region.setPrefSize(18, 18);
        region.setMinSize(18, 18);
        region.setMaxSize(18, 18);
        region.setStyle("-fx-background-color: " + color + ";");
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: #ececec; -fx-font-size: 16px;");
        hBox.getChildren().addAll(region, textLabel);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }
}