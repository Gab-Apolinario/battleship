package battleship.view;

import battleship.model.Board;
import battleship.model.CellState;
import battleship.model.Orientation;
import battleship.model.Ship;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BoardView {
    private Board board;
    private boolean hideShips; //tabuleiro inimigo não mostra os navios posicionados
    private boolean clickable; //tabuleiro clicável = botões para atacar
    private Button[][] buttons;
    private GridPane grid;
    private CellClickListener listener;
    private Ship selectedShip;

    public BoardView(Board board, boolean hideShips, boolean clickable) {
        this.board = board;
        this.hideShips = hideShips;
        this.clickable = clickable;
        buttons = new Button[board.getSize()][board.getSize()];

        
        //GRID
        grid = new GridPane(2, 2); //grid de botões (2,2) -> espaçamento entre os itens do grid
        grid.setAlignment(Pos.CENTER);
        
        //Letras topo do grid
        for (int j = 0; j < board.getSize(); j++) {
            Label letter = new Label(String.valueOf((char)('A' + j)));
            letter.setMinSize(50, 50);
            letter.setAlignment(Pos.CENTER);
            grid.add(letter, j + 1, 0);
        }

        //Numeros lado do grid
        for (int i = 0; i < board.getSize(); i++) {
            Label num = new Label(String.valueOf(i + 1));
            num.setMinSize(30, 50);
            num.setAlignment(Pos.CENTER);
            grid.add(num, 0, i + 1);
        }
        
        for (int i = 0; i < board.getSize(); i++) { //for para adicionar os botões nas linhas (i)/colunas (j) do grid
            for (int j = 0; j < board.getSize(); j++) {
                int row = i;
                int col = j;


                Button button = new Button(" ");
                button.setPrefSize(50, 50); //padroniza tamanho dos botões
                buttons[i][j] = button;
                button.setOnAction(e -> {
                    if (clickable && listener != null) {
                        listener.onCellClick(row, col);
                    }
                });

                button.setOnMouseEntered(e -> showPreview(row, col)); //se o mouse estiver em cima do botão, mostra preview
                button.setOnMouseExited(e -> refresh()); //se o mouse sai de cima do botão, atualiza grid

                grid.add(button, col + 1, row + 1); //Adiciona botão .add(node, col, row) do grid
            }
        }

        refresh();
    }

    public void setSelectedShip(Ship selectedShip) {
        this.selectedShip = selectedShip;
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
                if (hideShips && showState == CellState.SHIP) { // se for tabuleiro da IA, não mostra os navios
                    paintCell(buttons[i][j], CellState.WATER, i, j);
                } else {
                    paintCell(buttons[i][j], showState, i, j);
                }
            }
        }
    }

    private void paintCell(Button button, CellState state, int row, int col) {
        switch (state) {
            case WATER:
                //cor azul
                button.setStyle("-fx-background-color: #4582d2;");
                break;
            case WATER_HIT:
                //azul escuro
                button.setStyle("-fx-background-color: #1f3f68;");
                break;
            case SHIP:
                //cinza claro
                String style = "-fx-background-color: #6c6c6c;";
                if (board.getShipAt(row, col) == selectedShip) {
                    style += "-fx-border-color: gold; -fx-border-width: 3;";
                }
                button.setStyle(style);
                break;
            case SHIP_HIT:
                //cinza escuro OU vermelho
                button.setStyle("-fx-background-color: #5b0000;");
                break;
        }
    }

    //Mostra preview se pode ou não posicionar navio selecionado no local
    public void showPreview(int row, int col) {
        if (selectedShip == null || !clickable) {
            return;
        }

        boolean validPlace = board.canPlace(selectedShip, row, col, selectedShip.getOrientation());
        String color;
        for (int i = 0; i < selectedShip.getSize(); i++) {
            int cellRow; //guarda linha da célula [i]
            int cellCol; //guarda coluna da célula [i]
            
            if (selectedShip.getOrientation() == Orientation.HORIZONTAL) {
                cellRow = row; //H -> trava row
                cellCol = col + i; //H -> muda col
            } else {
                cellRow = row + i; //V -> muda row
                cellCol = col; //V -> trava col
            }
            
            
            if (board.isInside(cellRow, cellCol)) {
                if (validPlace) { //cor verde
                    color = "green";
                } else { //cor vemelha
                    color = "red";
                }
                Button b = buttons[cellRow][cellCol];
                b.setStyle(b.getStyle() + "-fx-border-color: " + color + "; -fx-border-width: 3;");
            }
        }
    }
}