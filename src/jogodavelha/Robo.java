package jogodavelha;

public class Robo {
    private final char skin;

    public Robo(char skinJogador){
        if(skinJogador == 'X'){
            this.skin = 'O';
        }
        else {
            this.skin = 'X';
        }
    }

    public char getSkin() {
        return skin;
    }
}
