package battleship.model;

public class Submarine extends Ship {
    public Submarine(int id, Orientation orientation) {
        super(id, "Submarine", 'S', 3, orientation);
    }

    @Override
    public String getDescription() {
        String output = String.format("Descrição do Navio = ID: %d | Nome: %s | Símbolo: %c | Tamanho: %d\nSubmarinos são considerados uma das armas mais letais do mundo pelo fator da invisibilidade e por carregarem mísseis nucleares!", getId(), getName(), getSymbol(), getSize());
        return output;
    }
}