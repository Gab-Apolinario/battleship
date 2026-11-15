package battleship.view;

import battleship.controller.GameController;
import battleship.model.Player;
import javafx.scene.Parent;

public abstract class Screen {
    protected GameController gameController;
    protected GameWindow window;
    protected static final String TITLE_FONT = "-fx-font-family: 'Impact';";
    protected static final String BODY_FONT = "-fx-font-family: 'Segoe UI';";

    public Screen(GameController gameController, GameWindow window) {
        this.gameController = gameController;
        this.window = window;
    }

    protected String battleStatusText(Player player) {
        return String.format("Tiros Disparados: %d | Acertos: %d | Afundados: %d",
            player.getShotsFired(), player.getHits(), player.getSunkEnemyShips());
    }

    public abstract Parent getRoot();
}