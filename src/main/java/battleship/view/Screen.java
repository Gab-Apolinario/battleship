package battleship.view;

import battleship.controller.GameController;
import battleship.model.Player;
import javafx.scene.Parent;

public abstract class Screen {
    protected GameController gameController;
    protected GameWindow window;

    public Screen(GameController gameController, GameWindow window) {
        this.gameController = gameController;
        this.window = window;
    }

    protected String statusText(Player player) {
        return String.format("Shots Fired: %d | Hits: %d | Misses: %d | Sunk Ships: %d | Turns Played: %d | SCORE: %d | Accuracy: %.2f",
            player.getShotsFired(), player.getHits(), player.getMisses(), player.getSunkEnemyShips(),
            player.getTurnsPlayed(), player.getScore(), player.getAccuracy());
    }

    public abstract Parent getRoot();
}