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
        return String.format("Tiros Disparados: %d | Acertos: %d | Erros: %d | Navios Inimigos Afundados: %d | Jogadas: %d | PONTUAÇÃO: %d | Taxa de Acertos: %.2f",
            player.getShotsFired(), player.getHits(), player.getMisses(), player.getSunkEnemyShips(),
            player.getTurnsPlayed(), player.getScore(), player.getAccuracy());
    }
    protected String battleStatusText(Player player) {
        return String.format("Tiros Disparados: %d | Acertos: %d | Navios Inimigos Afundados: %d",
            player.getShotsFired(), player.getHits(), player.getSunkEnemyShips());
    }

    public abstract Parent getRoot();
}