package battleship.view;

import battleship.model.Board;
import battleship.model.CellState;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardView {
    private Board board;
    private boolean hideShips; //tabuleiro inimigo não mostra os navios posicionados
    private boolean clickable; //tabuleiro clicável = botões para atacar
    private Button[][] buttons;
    private GridPane grid;
    private CellClickListener listener;

    public BoardView(Board board, boolean hideShips, boolean clickable) {
        this.board = board;
        this.hideShips = hideShips;
        this.clickable = clickable;
        buttons = new Button[board.getSize()][board.getSize()];

        //GRID
        grid = new GridPane(2, 2); //grid de botões (2,2) -> espaçamento entre os itens do grid
        grid.setAlignment(Pos.CENTER);
        for (int i = 0; i < board.getSize(); i++) { //for para adicionar os botões nas linhas (i)/colunas (j)
            for (int j = 0; j < board.getSize(); j++) {
                int row = i;
                int col = j;
                Button button = new Button(" ");
                button.setPrefSize(50, 50); //padroniza tamanho dos botões
                buttons[i][j] = button;
                button.setOnAction(e -> {
                    if (clickable && listener != null) {listener.onCellClick(row, col);}
                });

                grid.add(button, col, row); //Adiciona botão .add(node, col, row) do grid
            }
        }

        refresh();
    }

    public GridPane getGrid() {
        return this.grid;
    }

    public void setListener(CellClickListener listener) {
        this.listener = listener;
    }

    //Atualiza tabuleiro
    public void refresh() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                CellState showState = board.getCellState(i, j);
                if (hideShips && showState == CellState.SHIP) { //se for tabuleiro da IA, não mostra os navios
                    paintCell(buttons[i][j], CellState.WATER);
                } else {
                    paintCell(buttons[i][j], showState);
                }
            }
        }
    }

    private void paintCell(Button button, CellState state) {
        switch (state) {
            case WATER:
                //cor azul
                button.setStyle("-fx-background-color: #4582d2");
            break;
            case WATER_HIT:
                //azul escuro
                button.setStyle("-fx-background-color: #1f3f68");
                break;
            case SHIP:
                //cinza claro
                button.setStyle("-fx-background-color: #6c6c6c");
                break;
            case SHIP_HIT:
                //cinza escuro OU vermelho
                button.setStyle("-fx-background-color: #5b0000");
                break;
        }
    }
}