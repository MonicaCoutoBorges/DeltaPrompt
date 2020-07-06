package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Graphics;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Game {

    //criei aqui a array de players para controlar o numero de players e saber distinguir quem é quem!
    private final Player players[] = new Player[2];

    private ArrayList<Character> characters = new ArrayList<>();
    private char[] hiddenWord;
    private Prompt prompt;
    private Game_Server gameServer;
    private int maxRounds = 6;
    private int numberOfRounds = 0;
    private boolean isCorrect;
    private Player player;
    private String choosedWord;
    private Player_Handler player_handler;
    private boolean isVictorious;
    private char character;


    //criei este booleano com a intençao de o usar quando o user do intellij clika no enter e já ha uma palavra guardada
    private boolean wordChossed = false;

//removi o construtor porque não precisamos dele

    public void start() throws IOException {

        //aqui atribui o player tonyGay a primeira pos da array, o proprio intellij é o [0]
        Player tonyGay = players[1];

        //iniciei a prompt
        prompt = new Prompt(System.in, System.out);

        //mandar msg ao tony , fica a "espera" ate  o -> wordChossed <- passar a true ( linha 50)
        tonyGay.sendMessage(Message.WAITING_FOR_PLAYER + "\n");

        //pede a palavra secreta ao intellij e guarda-a na variavel choosedWord e printa-a na consola (só para teste)
        StringInputScanner userScanner = new StringInputScanner();
        userScanner.setMessage(Message.CHOOSE_A_WORD);
        choosedWord = prompt.getUserInput(userScanner).toLowerCase();
        wordChossed = true;  //quando o user do intellij clica enter e já ha palavra guardada , mudo para true, assim a logica da pessoa que usa a powershell, pára de ver o "waiting for player" e ja pode começar a logiica

        System.out.println(Message.WORD_CHOSEN + choosedWord);

        tonyGay.getPlayerHandler().sendMessageToPlayer(Message.OPPONENT_CHOSEN_WORD + "\n \n");


        while (maxRounds > 0) {

            for (Graphics picture : Graphics.values()){
                if (Graphics.getCounter() == picture.getId()){
                    tonyGay.getPlayerHandler().sendMessageToPlayer(picture.getPicture() + "\n");
                }
            }

            changeWordToChar(tonyGay);

            printHiddenWord(tonyGay);
            tonyGay.getPlayerHandler().sendMessageToPlayer(Message.PICK_CHAR + "\n");

            String playerPickChar = tonyGay.getPlayerHandler().pickChar() + "\n";
            char savingChar = playerPickChar.charAt(0);

            if(playerPickChar.length() > 3){

                tonyGay.getPlayerHandler().sendMessageToPlayer("Invalid Guess. Try Again.."+ "\n");
            }

            System.out.println("Character picked!");
//            char selectedChar = getPickedChars(tonyGay);
//            characters.add(selectedChar);


            if (!compareChars(savingChar)) {

                Graphics.incrementCounter();

                maxRounds--;
                tonyGay.getPlayerHandler().sendMessageToPlayer(Message.WRONG_GUESS + "\n");

                if (maxRounds == 0) {
                    tonyGay.getPlayerHandler().sendMessageToPlayer(Message.GAME_OVER+ "\n");
                    tonyGay.getPlayerHandler().sendMessageToPlayer(Graphics.STEP6.getPicture()+ "\n");
                    System.out.println(Message.GAME_OVER);
                    start();

                    //Aqui podemos fazer uma cena fixe de prompt que o joao falou.

                } else {
                    System.out.println(savingChar);
                    System.out.println("Keep going!");
                }
            } else {

                tonyGay.getPlayerHandler().sendMessageToPlayer("YOU GOT IT RIGHT!"+ "\n");

                if (checkWordIsComplete()){

                    tonyGay.getPlayerHandler().sendMessageToPlayer("You have won! Congratulations!" + "\n");
                    start();

                }

            }

            }

        }



    public void changeWordToChar(Player players) throws IOException {


        if (wordChossed) {

            //aqui começar a logica -> meter ele a ver os ------- etc etc  --> NAO SEI SE ESTÁ CERTO ASSIM <--
            hiddenWord = new char[choosedWord.length()];

            for (int i = 0; i < choosedWord.length(); i++) {
                hiddenWord[i] += '-';
            }
            char[] a = hiddenWord;
            String str = new String(a);
//            players.sendMessage(str + "\n");
//            players.sendMessage(Message.PICK_CHAR + "\n");
            wordChossed = false;

        }

    }

    public boolean compareChars(char character) {

        boolean foundChar = false;

        for (int i = 0; i < choosedWord.length(); i++) {
            if (character == choosedWord.charAt(i)) {
                hiddenWord[i] = character;
                foundChar = true;
            }
        }
        return foundChar;
    }

    public boolean checkWordIsComplete(){

        boolean isComplete = true;

        for (int i = 0; i < hiddenWord.length; i++) {
            if (hiddenWord[i] == '-') {
                isComplete = false;
            }
        }

        return isComplete;

    }

    public void printHiddenWord(Player tonyGay){

        char[] a = hiddenWord;
        String str = new String(a);
        tonyGay.getPlayerHandler().sendMessageToPlayer("Guess this word: \"" + str + "\"" + "\n");


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

    public char getPickedChars(Player players) {

        String wordPicked = players.pickLetter();
        char charPicked = wordPicked.charAt(0);

        for (Character character : characters) {
            if (character.compareTo(character) == 0) {
                sendMessageToAll(character + Message.ALREADY_IN_USE + "\n");
                charPicked = getPickedChars(players);
            }
        }
        return charPicked;
    }


}

