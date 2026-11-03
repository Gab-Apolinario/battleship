package battleship.model;

public class Cruiser extends Ship {
    public Cruiser(int id, Orientation orientation) {
        super(id, "Cruiser", 'C', 3, orientation);
    }

    @Override
    public String getDescription() {
        String output = String.format("Descrição do Navio = ID: %d | Nome: %s | Símbolo: %c | Tamanho: %d\nO cruzador é um navio de guerra de alta velocidade. Historicamente conhecido por missões de exploração!", getId(), getName(), getSymbol(), getSize());
        return output;
    }
}