package battleship.model;

public class Battleship extends Ship {
    public Battleship(int id, Orientation orientation) {
        super(id, "Battleship", 'B', 4, orientation);
    }

    @Override
    public String getDescription() {
        String output = String.format("Descrição do Navio = ID: %d | Nome: %s | Símbolo: %c | Tamanho: %d\nO encouraçado é um navio de guerra equipado com artilharia pesada de longo alcance!", getId(), getName(), getSymbol(), getSize());
        return output;
    }
}