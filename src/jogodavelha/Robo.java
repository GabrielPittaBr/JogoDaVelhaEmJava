package jogodavelha;

// Por: Gabriel Fernandes Pitta

import java.util.Random;

public class Robo extends Jogador{

    public Robo(String nome, char skinJogador){
        super(nome, skinOposta(skinJogador));
    }

    @Override
    public int fazerJogada(Tabuleiro tabuleiro, int jogadaAtual) {
        Random aleatorio = new Random();

        int contadorPosicoesLivres = tabuleiro.verificarContadorPosicoesLivres();

        int indiceJogadaLinearDoRobo = aleatorio.nextInt(contadorPosicoesLivres);

        int[] posicoesDisponiveis = tabuleiro.verificarPosicoesDisponiveis();

        IO.println("O "+this.getNome()+" jogou na posição %d".formatted(posicoesDisponiveis[indiceJogadaLinearDoRobo]+1));

        return posicoesDisponiveis[indiceJogadaLinearDoRobo];
    }
}
