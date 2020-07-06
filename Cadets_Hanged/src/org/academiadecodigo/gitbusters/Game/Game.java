package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Graphics;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Game {

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
    private boolean wordChossed = false;

    public void start() throws IOException {

        Player hostGame = players[0];
        Player firstPlayer = players[1];
        PrintStream printStream = new PrintStream(hostGame.getPlayerHandler().getClientSocket().getOutputStream());
        Prompt prompt = new Prompt(hostGame.getPlayerHandler().getClientSocket().getInputStream(), printStream);

        firstPlayer.sendMessage(Message.WAITING_FOR_PLAYER + "\n");

        StringInputScanner userScanner = new StringInputScanner();
        userScanner.setMessage(Message.CHOOSE_A_WORD);
        choosedWord = prompt.getUserInput(userScanner).toLowerCase();
        wordChossed = true;

        System.out.println(Message.WORD_CHOSEN + choosedWord);
        firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.OPPONENT_CHOSEN_WORD + "\n \n");

        while (maxRounds > 0) {

            for (Graphics picture : Graphics.values()) {
                if (Graphics.getCounter() == picture.getId()) {
                    firstPlayer.getPlayerHandler().sendMessageToPlayer(picture.getPicture() + "\n");
                }
            }

            changeWordToChar(firstPlayer);
            printHiddenWord(firstPlayer);
            firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.PICK_CHAR + "\n");

            String playerPickChar = firstPlayer.getPlayerHandler().pickChar() + "\n";
            char savingChar = playerPickChar.charAt(0);

            if (playerPickChar.length() > 3) {

                firstPlayer.getPlayerHandler().sendMessageToPlayer("Invalid Guess. Try Again.." + "\n");
            }

            System.out.println("Character picked!");

            if (!compareChars(savingChar)) {

                Graphics.incrementCounter();

                maxRounds--;
                firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.WRONG_GUESS + "\n");

                if (maxRounds == 0) {
                    firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.GAME_OVER + "\n");
                    firstPlayer.getPlayerHandler().sendMessageToPlayer(Graphics.STEP6.getPicture() + "\n");
                    System.out.println(Message.GAME_OVER);
                    start();

                } else {
                    System.out.println(savingChar);
                    System.out.println("Keep going!");
                }
            } else {

                firstPlayer.getPlayerHandler().sendMessageToPlayer("YOU GOT IT RIGHT!" + "\n");

                if (checkWordIsComplete()) {

                    firstPlayer.getPlayerHandler().sendMessageToPlayer("You have won! Congratulations!" + "\n");
                    start();
                }
            }
        }
    }

    public void changeWordToChar(Player players) throws IOException {

        if (wordChossed) {

            hiddenWord = new char[choosedWord.length()];

            for (int i = 0; i < choosedWord.length(); i++) {
                hiddenWord[i] += '-';
            }
            char[] a = hiddenWord;
            String str = new String(a);
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

    public boolean checkWordIsComplete() {

        boolean isComplete = true;

        for (int i = 0; i < hiddenWord.length; i++) {
            if (hiddenWord[i] == '-') {
                isComplete = false;
            }
        }

        return isComplete;
    }

    public void printHiddenWord(Player tonyGay) {

        char[] a = hiddenWord;
        String str = new String(a);
        tonyGay.getPlayerHandler().sendMessageToPlayer("Guess this word: \"" + str + "\"" + "\n");
    }

    public void sendMessageToAll(String message) {

    }

    void playerMaker(int playerId, Player_Handler player_handler) {

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

