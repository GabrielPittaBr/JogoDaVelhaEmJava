package jogodavelha;

import java.util.Random;

public class RoboDificil extends Robo {

    public RoboDificil(String nome, char skinJogador) {
        super(nome, skinJogador);
    }

    @Override
    public int fazerJogada(Tabuleiro tabuleiro, int jogadaAtual) {
        char minhaSkin = this.getSkin();
        char skinAdversario = Jogador.skinOposta(minhaSkin);

        int posicao = encontrarJogadaQueVence(tabuleiro, minhaSkin);

        if (posicao == -1) {
            posicao = encontrarJogadaQueVence(tabuleiro, skinAdversario);
        }
        if (posicao == -1) {
            posicao = escolherPosicaoEstrategica(tabuleiro);
        }

        IO.println("O "+this.getNome()+" jogou na posição %d".formatted(posicao + 1));

        return posicao;
    }

    private int encontrarJogadaQueVence(Tabuleiro tabuleiro, char skin) {
        int[] posicoesDisponiveis = tabuleiro.verificarPosicoesDisponiveis();
        int contadorPosicoesLivres = tabuleiro.verificarContadorPosicoesLivres();

        for (int i = 0; i < contadorPosicoesLivres; i++) {
            int posicao = posicoesDisponiveis[i];

            tabuleiro.escreverJogada(posicao, skin);
            char resultado = tabuleiro.verificaFimDeJogo();
            tabuleiro.escreverJogada(posicao, ' ');

            if (resultado == skin) {
                return posicao;
            }
        }

        return -1;
    }

    private int escolherPosicaoEstrategica(Tabuleiro tabuleiro) {
        int[] posicoesDisponiveis = tabuleiro.verificarPosicoesDisponiveis();
        int contadorPosicoesLivres = tabuleiro.verificarContadorPosicoesLivres();

        for (int i = 0; i < contadorPosicoesLivres; i++) {
            if (posicoesDisponiveis[i] == 4) {
                return 4;
            }
        }

        int[] cantos = {0, 2, 6, 8};
        for (int canto : cantos) {
            for (int i = 0; i < contadorPosicoesLivres; i++) {
                if (posicoesDisponiveis[i] == canto) {
                    return canto;
                }
            }
        }

        Random aleatorio = new Random();
        return posicoesDisponiveis[aleatorio.nextInt(contadorPosicoesLivres)];
    }
}
