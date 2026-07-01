package jogodavelha;

// Por: Gabriel Fernandes Pitta

public class Tabuleiro {
    private final char[][] jogoDaVelha;

    public Tabuleiro() {
        this.jogoDaVelha = new char[][]{{' ',' ',' '}, {' ',' ',' '}, {' ',' ',' '}};
    }

    public int[] verificarPosicoesDisponiveis() {
        int[] posicoesLivres = new int[9];
        int numeroPosicao;
        int contadorPosicoesLivres = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (jogoDaVelha[i][j] == ' '){
                    numeroPosicao = (i * 3) + j;
                    posicoesLivres[contadorPosicoesLivres] = numeroPosicao;
                    contadorPosicoesLivres++;
                }
            }
        }

        return posicoesLivres;
    }

    public int verificarContadorPosicoesLivres() {
        int contadorPosicoesLivres = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (jogoDaVelha[i][j] == ' '){
                    contadorPosicoesLivres++;
                }
            }
        }

        return contadorPosicoesLivres;
    }

    public void escreverJogada(int posicaoLinear, char skin) {
        int linha = posicaoLinear / 3;
        int coluna = posicaoLinear % 3;

        jogoDaVelha[linha][coluna] = skin;
    }

    public void imprimirJogoAtual(String mensagem) {
        IO.println(mensagem);
        String linha = "";
        for (int i = 0; i < 3; i++) {
            linha = "| ";
            for (int j = 0; j < 3; j++) {
                linha += jogoDaVelha[i][j] + " | ";
            }
            IO.println(linha);
        }
    }

    public char verificaFimDeJogo() {
        for (int i = 0; i < 3; i++) {
            if (jogoDaVelha[i][0] != ' ' && jogoDaVelha[i][0] == jogoDaVelha[i][1] && jogoDaVelha[i][1] == jogoDaVelha[i][2]) {
                return jogoDaVelha[i][0];
            }
        }

        for (int j = 0; j < 3; j++) {
            if (jogoDaVelha[0][j] != ' ' && jogoDaVelha[0][j] == jogoDaVelha[1][j] && jogoDaVelha[1][j] == jogoDaVelha[2][j]) {
                return jogoDaVelha[0][j];
            }
        }

        if (jogoDaVelha[0][0] != ' ' && jogoDaVelha[0][0] == jogoDaVelha[1][1] && jogoDaVelha[1][1] == jogoDaVelha[2][2]) {
            return jogoDaVelha[0][0];
        }
        else if (jogoDaVelha[0][2] != ' ' && jogoDaVelha[0][2] == jogoDaVelha[1][1] && jogoDaVelha[1][1] == jogoDaVelha[2][0]) {
            return jogoDaVelha[0][2];
        }

        return ' ';
    }

    public int verificarPosicaoJogadorValida() {
        int[] posicoesValidas = verificarPosicoesDisponiveis();
        boolean escolhaOk;
        boolean escolhaEstaDisponivel = false;
        int escolha = 0;

        do {
            try {
                escolha = Integer.parseInt(IO.readln("> ")) - 1;
                for (int i = 0; i < verificarContadorPosicoesLivres() && !escolhaEstaDisponivel; i++) {
                    if (escolha == posicoesValidas[i]){
                        escolhaEstaDisponivel = true;
                    }
                }
                if (escolha < 0 || escolha > 8){
                    IO.println("Por favor, digite um número entre 1 a 9.");
                    escolhaOk = false;
                }
                else if (!escolhaEstaDisponivel) {
                    IO.println("Essa casa já está preenchida.\nDigite uma opção que ainda está disponível.");
                    escolhaOk = false;
                }
                else {
                    escolhaOk = true;
                }
            }
            catch (NumberFormatException erroDigitouLetra) {
                IO.println("Por favor, digite um número.");
                escolhaOk = false;
            }
            catch (Exception erro){
                IO.println("Erro desconhecido.");
                IO.println(erro);
                escolhaOk = false;
            }
        } while (!escolhaOk);

        return escolha;
    }
}
