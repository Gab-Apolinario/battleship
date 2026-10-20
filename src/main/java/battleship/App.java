package battleship;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) { //'stage' = the window open. The name can be changed (has a title, minimize/exit button)
        Label label = new Label("Batalha Naval");
        label.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(label, 400, 400)); //'Scene' is what shows in the window
        stage.setTitle("Stage Title - Batalha Naval");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
