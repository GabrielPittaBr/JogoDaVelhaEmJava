package jogodavelha;

// Por: Gabriel Fernandes Pitta

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

    public static char skinOposta(char skin) {
        if (skin == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    public int fazerJogada(Tabuleiro tabuleiro, int jogadaAtual){
        if (jogadaAtual == 0){
            IO.println("Faça a primeira jogada, baseado nos números das posições:");
        }
        else {
            IO.println("Números das posições:");
        }
        IO.println("| 1 | 2 | 3 |");
        IO.println("| 4 | 5 | 6 |");
        IO.println("| 7 | 8 | 9 |");

        tabuleiro.imprimirJogoAtual("Jogo atual:");

        IO.println("Vez de "+this.getNome()+":");

        int escolha = tabuleiro.verificarPosicaoJogadorValida();

        return escolha;
    }
}
