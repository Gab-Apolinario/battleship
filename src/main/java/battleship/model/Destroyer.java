package battleship.model;

public class Destroyer extends Ship {
    public Destroyer(int id, Orientation orientation) {
        super(id, "Destroyer", 'D', 2, orientation);
    }

    @Override
    public String getDescription() {
        String output = String.format("Descrição do Navio = ID: %d | Nome: %s | Símbolo: %c | Tamanho: %d\nA fragata é um navio altamente tecnológico e versátil!", getId(), getName(), getSymbol(), getSize());
        return output;
    }
}