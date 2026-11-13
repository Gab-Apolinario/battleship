package battleship.view;

import battleship.controller.GameController;
import battleship.model.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) { //'stage' = the window open. The name can be changed (has a title, minimize/exit button)
        GameController gameController = new GameController();
        GameWindow gameWindow = new GameWindow(stage, gameController);
        stage.setTitle("Batalha Naval");
        gameWindow.show();

        
        /*GRID
        GridPane grid = new GridPane(10, 10); //GridPane = grid of buttons
        //grid.setGridLinesVisible(true);
        for (int i = 0; i < 10; i++) //for para adicionar os botões nas linhas (i)/colunas (j)
            for (int j = 0; j < 10; j++) {
                int row = i;
                int col = j;
                String letter = Board.coordinateKey(row, col);
                Button botao = new Button(" ");
                botao.setPrefSize(50, 50);
                botao.setOnAction(e -> botao.setText("" + letter)); //Ativa cada botão -> morre com o loop, mas o grid salva
                grid.add(botao, col, row); //Adiciona botão .add(node, col, row) do grid
            }
            
        stage.setScene(new Scene(grid, 600, 600));
        stage.show();
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
