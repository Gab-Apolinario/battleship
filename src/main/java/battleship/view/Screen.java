package battleship.view;

import battleship.controller.GameController;
import javafx.scene.Parent;

public abstract class Screen {
    protected GameController gameController;
    protected GameWindow window;

    public Screen(GameController gameController, GameWindow window) {
        this.gameController = gameController;
        this.window = window;
    }

    public abstract Parent getRoot();
}