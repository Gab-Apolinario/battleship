package battleship.model;

public class Carrier extends Ship {
    public Carrier(int id, Orientation orientation) {
        super(id, "Carrier", 'A', 5, orientation);
    }

    @Override
    public String getDescription() {
        String output = String.format("Descrição do Navio = ID: %d | Nome: %s | Símbolo: %c | Tamanho: %d\nO porta-aviões é o maior navio da frota, ocupa %d espaços!", getId(), getName(), getSymbol(), getSize(), getSize());
        return output;
    }
}