package battleship.model;

public class HumanPlayer extends Player {
    public HumanPlayer(String name, Board board) {
        super(name, board);
    }

    @Override
    public boolean isAutomatic() {
        return false;
    }
}