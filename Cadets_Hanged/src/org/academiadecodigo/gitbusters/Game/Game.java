package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    //criei aqui a array de players para controlar o numero de players e saber distinguir quem é quem!
    private final Player players[] = new Player[2];

    private ArrayList<Character> characters = new ArrayList<>();
    private String guessingWord;
    private char[] hiddenWord;
    private Prompt prompt;
    private Game_Server gameServer;
    private int maxRounds = 6;
    private int numberOfRounds = 0;
    private boolean isCorrect;
    private Player player;
    private int numeroJogadores = 2;

    //criei este booleano com a intençao de o usar quando o user do intellij clika no enter e já ha uma palavra guardada
    private boolean wordChossed = false;

//removi o construtor porque não precisamos dele

    public void start() throws IOException {

        //aqui atribui o player tonyGay a primeira pos da array, o proprio intellij é o [0]

        Player tonyGay = players[1];
        //iniciei a prompt

        prompt = new Prompt(System.in,System.out);

        //mandar msg ao tony
        tonyGay.sendMessage("Wait for your opponent..." + "\n");

        //pede a palavra secreta ao intellij e guarda-a na variavel choosedWord e printa-a na consola (só para teste)
        StringInputScanner userScanner = new StringInputScanner();
        userScanner.setMessage("Choose one word: ");
        String choosedWord = prompt.getUserInput(userScanner);
        wordChossed = true;  //quando o user do intellij clica enter e já ha palavra guardada , mudo para true, assim a logica da pessoa que usa a powershell, pára de ver o "waiting for player" e ja pode começar a logiica
        System.out.println("Word choosed was: "+ choosedWord);

        //se houver palavra, começar a logica.
            if (wordChossed = true){
                tonyGay.sendMessage("Your opponent already choosed the word" + "\n");

                //aqui começar a logica -> meter ele a ver os ------- etc etc

            }
   }

    public void wordToChar() {
        hiddenWord = new char[guessingWord.length()];
        for (int i = 0; i < guessingWord.length(); i++) {
            hiddenWord[i] += '-';
        }
        sendMessageToAll(" " + hiddenWord + maxRounds + Message.GUESSES_LEFT + "\n");
    }

    private boolean compareChars(char userInput) {
        for (int i = 0; i < guessingWord.length(); i++) {
            if (userInput == guessingWord.charAt(i)) {
                hiddenWord[i] = userInput;
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    public void sendMessageToAll(String message) {
       // for (Player player : players) {
            //por aqui uma condição que faça com que não envie sms para o player que enviou (TCP)
           // player.sendMessage(message);
        //}
    }

    // IMPORTANTE!!! metodo que cria o player recebento o id e o handler. metodo esse que vai ser chamado na main
    //ele na posiçao 1 ,  cria um novo player lá. na posiçao 0 já está o intellij

    void createPlayer(int playerId, Player_Handler player_handler) {
        players[playerId] = new Player(player_handler);
    }
}
