package jogodavelha;

// Por: Gabriel Fernandes Pitta

import java.util.Random;

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

    static String pedirNome() {
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

        return nomeJogador;
    }

    static char pedirSkin() {
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

        return skinJogador;
    }

    static int quemComeca(String nomeJogador1, String nomeJogador2) {
        Random aleatorio = new Random();

        IO.println("Sorteando o primeiro a jogar...");

        int primeiroAjogar = aleatorio.nextInt(2);
        if (primeiroAjogar == 1) {
            IO.println("%s começa jogando.".formatted(nomeJogador1));
        }
        else {
            IO.println("%s começa jogando.".formatted(nomeJogador2));
        }

        return primeiroAjogar;
    }

    static int proximaJogada(int proximoAjogar, int jogadaAtual, Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2) {
        if (proximoAjogar == 1){
            int posicaoLinearJogador1 = jogador1.fazerJogada(tabuleiro, jogadaAtual);
            tabuleiro.escreverJogada(posicaoLinearJogador1, jogador1.getSkin());
            proximoAjogar--;
        }
        else {
            int posicaoLinearJogador2 = jogador2.fazerJogada(tabuleiro, jogadaAtual);
            tabuleiro.escreverJogada(posicaoLinearJogador2, jogador2.getSkin());
            proximoAjogar++;
        }

        return proximoAjogar;
    }

    static void quemGanhou(char vencedor, Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiro) {
        if (jogador1.getSkin() == vencedor){
            IO.println("O vencedor é: "+jogador1.getNome()+"! Não foi dessa vez, "+jogador2.getNome()+"!");
        }
        else if (jogador2.getSkin() == vencedor) {
            IO.println("O vencedor é: "+jogador2.getNome()+"! Não foi dessa vez, "+jogador1.getNome()+"!");
        }
        else {
            IO.println("Deu velha! Ninguém ganhou o jogo.");
        }

        tabuleiro.imprimirJogoAtual("Jogo final:");
    }

    static void jogarPartida(Jogador jogador1, Jogador jogador2) {
        Tabuleiro jogoDaVelha = new Tabuleiro();
        boolean fim = false;
        int jogadaAtual = 0;
        char vencedor;

        int proximoAjogar = quemComeca(jogador1.getNome(), jogador2.getNome());

        do {
            proximoAjogar = proximaJogada(proximoAjogar, jogadaAtual, jogoDaVelha, jogador1, jogador2);
            vencedor = jogoDaVelha.verificaFimDeJogo();
            if(vencedor != ' '){
                fim = true;
            }
            else if (jogoDaVelha.verificarContadorPosicoesLivres() == 0) {
                fim = true;
            }
            else {
                jogadaAtual++;
            }
        } while (!fim);

        quemGanhou(vencedor, jogador1, jogador2, jogoDaVelha);
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

    static int escolherDificuldade() {
        int dificuldade = 0;
        boolean erro;

        do {
            try {
                IO.println("Escolha a dificuldade do robô:");
                IO.println("1 - Fácil");
                IO.println("2 - Difícil");
                dificuldade = Integer.parseInt(IO.readln("> "));
                if (dificuldade != 1 && dificuldade != 2) {
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

        return dificuldade;
    }

    static void jogoContraRobo(){
        IO.println("-=-=- Você selecionou: 1 - Jogar contra Robô -=-=-");

        Jogador jogador1 = new Jogador(pedirNome(), pedirSkin());

        int dificuldade = escolherDificuldade();

        Robo robo = switch (dificuldade) {
            case 2 -> new RoboDificil("Robô difícil", jogador1.getSkin());
            default -> new Robo("Robô fácil", jogador1.getSkin());
        };

        jogarPartida(jogador1, robo);
    }

    static void jogoContraOutraPessoa() {
        IO.println("-=-=- Você selecionou: 2 - Jogar contra outra pessoa -=-=-");

        IO.println("Jogador 1:");
        Jogador jogador1 = new Jogador(pedirNome(), pedirSkin());

        IO.println("Jogador 2:");
        Jogador jogador2 = new Jogador(pedirNome(), Jogador.skinOposta(jogador1.getSkin()));

        IO.println("O "+jogador2.getNome()+" ficou com "+Jogador.skinOposta(jogador1.getSkin())+".");

        jogarPartida(jogador1, jogador2);
    }

    static void oQueEisso() {

    }

    static void main() {
        bemVindo();
        boolean jogarDeNovo = false;
        do {
            int escolha = telaInicial();
            switch (escolha){
                case 1 -> {
                    jogoContraRobo();
                    jogarDeNovo = jogarNovamente();
                }
                case 2 -> {
                    jogoContraOutraPessoa();
                    jogarDeNovo = jogarNovamente();
                }
                case 3 -> {
                    IO.println("-=-=- Você selecionou: 3 - O que é isso? -=-=-");
                    oQueEisso();
                    jogarDeNovo = jogarNovamente();
                }
                default -> IO.println("Erro desconhecido");

            }
        } while (jogarDeNovo);
        IO.println("Obrigado por jogar!");
    }
}
