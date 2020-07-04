package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.util.ArrayList;

public class Game {

    private final Player players[];
    private ArrayList<Character> characters = new ArrayList<>();
    private String guessingWord;
    private char[] hiddenWord;
    private Prompt prompt;
    private Game_Server gameServer;
    private int maxRounds = 6;
    private int numberOfRounds = 0;
    private boolean isCorrect;
    private Player player;

    public Game(int players) {
        this.players = new Player[2];
        this.gameServer = new Game_Server();
    }

    public void start() {
        System.out.println("aaaaaaaaaaaaaaaaaaa");

        //n esquecer maxrounds decrementa!!!!!
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
        for (Player player : players) {
            //por aqui uma condição que faça com que não envie sms para o player que enviou (TCP)
            player.sendMessage(message);
        }
    }
}
