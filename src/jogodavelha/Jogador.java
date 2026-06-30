package jogodavelha;

public class Jogador {
    private final String nome;
    private final char skin;

    public Jogador (String nome, char skin){
        this.nome = nome;
        this.skin = skin;
    }

    public String getNome() {
        return nome;
    }

    public char getSkin() {
        return skin;
    }
}
