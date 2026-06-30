package jogodavelha;

import java.util.Random;

/*
TODO: tirar isso
matriz 3x3:
| 00 | 01 | 02 |
| 10 | 11 | 12 |
| 20 | 21 | 22 |

| 1 | 2 | 3 |
| 4 | 5 | 6 |
| 7 | 8 | 9 |
 */
public class Principal {
    static void bemVindo() {
        IO.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        IO.println("         Bem vindo ao Super Jogo da Velha!");
        IO.println("               Por: Gabriel Pitta");
        IO.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        IO.println("");
    }

    static int telaInicial() {
        int opcaoDeJogo = 0;
        boolean erro;
        do {
            try {
                IO.println("Escolha uma opção:");
                IO.println("1 - Jogar contra Robô");
                IO.println("2 - Jogar contra outra pessoa");
                IO.println("3 - O que é isso?");
                opcaoDeJogo = Integer.parseInt(IO.readln("> "));
                if (opcaoDeJogo > 3 || opcaoDeJogo < 1) {
                    IO.println("Por favor, digite uma opção válida.");
                    erro = true;
                } else {
                    erro = false;
                }
            } catch (NumberFormatException erroDigitouLetra) {
                IO.println("Por favor, digite um número!");
                erro = true;
            } catch (Exception erroDesconhecido) {
                IO.println("Algum erro desconhecido aconteceu.");
                IO.println(erroDesconhecido);
                erro = true;
            }
        } while (erro);
        return opcaoDeJogo;
    }

    static Jogador criarNovoJogador(){
        boolean nomeOk = false;
        String nomeJogador = "";

        while (!nomeOk) {
            IO.println("Digite seu nome: ");
            nomeJogador = IO.readln("> ");
            if (nomeJogador.length() > 100) {
                IO.println("O nome é muito grande.");
            }
            else {
                nomeOk = true;
            }
        }

        boolean skinOk = false;
        char skinJogador = ' ';

        while (!skinOk) {
            IO.println("Você deseja jogar com X ou O?");
            skinJogador = IO.readln("> ").toUpperCase().charAt(0);
            if (skinJogador != 'X' && skinJogador != 'O') {
                IO.println("Você só pode escolher entre X e O.");
            }
            else {
                skinOk = true;
            }
        }

        return new Jogador(nomeJogador, skinJogador);
    }

    static int verificarPosicaoJogadorValida(char[][] matrizAtual){
        int[] posicoesValidas = verificarPosicoesDisponiveis(matrizAtual);
        boolean escolhaOk;
        boolean escolhaEstaDisponivel = false;
        int escolha = 0;

        do {
            try {
                escolha = Integer.parseInt(IO.readln("> "));
                for (int i = 0; i < posicoesValidas.length; i++) {
                    if (escolha == posicoesValidas[i]){
                        escolhaEstaDisponivel = true;
                    }
                }
                if (escolha < 1 || escolha > 9){
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

                escolhaOk = true;
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

    static int turnoDoJogador(char[][] matrizAtual, int jogadaAtual) {
        boolean posicaoValida = false;

        if (jogadaAtual == 0){
            IO.println("Faça a primeira jogada, baseado nos números das posições:");
        }
        else {
            IO.println("Números das posições:");
        }
        IO.println("| 1 | 2 | 3 |");
        IO.println("| 4 | 5 | 6 |");
        IO.println("| 7 | 8 | 9 |");

        IO.println("Jogo atual:");
        String linha = "";
        for (int i = 0; i < 3; i++) {
            linha = "| ";
            for (int j = 0; j < 3; j++) {
                linha += matrizAtual[i][j] + " | ";
            }
        }
        IO.println(linha);

        int escolha = verificarPosicaoJogadorValida(matrizAtual) - 1;

        return escolha;
    }

    static int[] verificarPosicoesDisponiveis(char[][] matrizAtual) {
        int[] posicoesLivres = new int[9];
        int numeroPosicao;
        int contadorPosicoesLivres = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrizAtual[i][j] == ' '){
                    numeroPosicao = (i * 3) + j;
                    posicoesLivres[contadorPosicoesLivres] = numeroPosicao;
                    contadorPosicoesLivres++;
                }
            }
        }

        return posicoesLivres;
    }

    static int verificarContadorPosicoesLivres(char[][] matrizAtual) {
        int contadorPosicoesLivres = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrizAtual[i][j] == ' '){
                    contadorPosicoesLivres++;
                }
            }
        }

        return contadorPosicoesLivres;
    }

    static int turnoDoRoboFacil(char[][] matrizAtual) {
        Random aleatorio = new Random();

        int contadorPosicoesLivres = verificarContadorPosicoesLivres(matrizAtual);

        return aleatorio.nextInt(contadorPosicoesLivres);
    }

    static void escreverJogadaNaMatrizAtual(int posicaoLinear, char[][] matrizAtual, char skin){
        int linha = posicaoLinear / 3;
        int coluna = posicaoLinear % 3;

        matrizAtual[linha][coluna] = skin;
    }

    static int quemComeca() {
        Random aleatorio = new Random();

        IO.println("Sorteando o primeiro a jogar...");

        int primeiroAjogar = aleatorio.nextInt(1);
        if (primeiroAjogar == 1) {
            IO.println("Você começa jogando.");
        }
        else {
            IO.println("O Robô começa jogando.");
        }

        return primeiroAjogar;
    }

    static int proximaJogada(int proximoAjogar, int jogadaAtual, char[][] matrizAtual, Jogador jogador, Robo robo) {
        if (proximoAjogar == 1){
            int posicaoLinearJogador = turnoDoJogador(matrizAtual, jogadaAtual);
            escreverJogadaNaMatrizAtual(posicaoLinearJogador, matrizAtual, jogador.getSkin());
            jogadaAtual++;
            proximoAjogar--;
        }
        else {
            int posicaoLinearRoboFacil = turnoDoRoboFacil(matrizAtual);
            escreverJogadaNaMatrizAtual(posicaoLinearRoboFacil, matrizAtual, robo.getSkin());
            jogadaAtual++;
            proximoAjogar++;
        }

        return proximoAjogar;
    }

    static boolean verificaGanhador(char[][] matrizAtual){
        //TODO - fazer a logica de verificar ganhador
    }

    static void jogarContraRoboFacil(Jogador jogador, Robo robo) {
        char[][] matrizJogoDaVelha = new char[3][3];
        boolean fim = false;
        int jogadaAtual = 0;

        int proximoAjogar = quemComeca();

        do {

            proximoAjogar = proximaJogada(proximoAjogar, jogadaAtual, matrizJogoDaVelha, jogador, robo);
            //fim = verificaGanhador(matrizJogoDaVelha);

        } while (!fim);
    }

    static void jogarContraOutraPessoa() {

    }

    static void oQueEisso() {

    }

    static boolean jogarNovamente() {
        boolean respValida = false;
        char resp = ' ';

        while (!respValida) {
            IO.println("Deseja jogar novamente? s/n");
            resp = IO.readln("> ").toLowerCase().charAt(0);
            if (resp != 's' && resp != 'n') {
                IO.println("Por favor, digite uma resposta válida.");
            }
            else {
                respValida = true;
            }
        }
        return resp == 's';
    }

    static void jogoContraRoboFacil(){
        IO.println("-=-=- Você selecionou: 1 - Jogar contra Robô -=-=-");
        Jogador jogador1 = criarNovoJogador();
        Robo roboFacil = new Robo(jogador1.getSkin());
        jogarContraRoboFacil(jogador1, roboFacil);
    }

    static void main() {
        bemVindo();
        boolean jogarDeNovo = false;
        do {
            int escolha = telaInicial();
            switch (escolha){
                case 1 -> {
                    jogoContraRoboFacil();
                    boolean jogarDeNovo = jogarNovamente();
                }
                case 2 -> {
                    IO.println("-=-=- Você selecionou: 2 - Jogar contra outra pessoa -=-=-");
                    jogarContraOutraPessoa();
                    jogarDeNovo = jogarNovamente();
                }
                case 3 -> {
                    IO.println("-=-=- Você selecionou: 3 - O que é isso? -=-=-");
                    oQueEisso();
                    jogarDeNovo = jogarNovamente();
                }
                default -> {
                    IO.println("Erro extremo");
                }
            }
        } while (jogarDeNovo);
    }
}
