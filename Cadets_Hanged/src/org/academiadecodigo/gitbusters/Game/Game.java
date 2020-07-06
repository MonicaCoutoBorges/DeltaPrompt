package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.PlayerHandler;
import org.academiadecodigo.gitbusters.Utility.Graphics;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.io.PrintStream;

public class Game {

    private final Player players[] = new Player[2];
    private char[] hiddenWord;
    private int maxRounds = 6;
    private int numberOfRounds = 0;
    private String chosenWord;
    private boolean isChosen = false;

    public void start() throws IOException {

        Player hostGame = players[0];
        Player firstPlayer = players[1];
        PrintStream printStream = new PrintStream(hostGame.getPlayerHandler().getClientSocket().getOutputStream());
        Prompt prompt = new Prompt(hostGame.getPlayerHandler().getClientSocket().getInputStream(), printStream);

        firstPlayer.sendMessage(Message.WAITING_FOR_PLAYER + "\n");

        StringInputScanner userScanner = new StringInputScanner();
        userScanner.setMessage(Message.CHOOSE_A_WORD);
        chosenWord = prompt.getUserInput(userScanner).toLowerCase();
        isChosen = true;

        System.out.println(Message.WORD_CHOSEN + chosenWord);
        firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.OPPONENT_CHOSEN_WORD + "\n \n");

        while (maxRounds > 0) {

            hangMaker(firstPlayer);
            changeWordToChar(firstPlayer);
            printHiddenWord(firstPlayer);

            firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.PICK_CHAR + "\n");

            String playerPickChar = firstPlayer.getPlayerHandler().pickChar() + "\n";

            char savingChar = playerPickChar.charAt(0);

            if (playerPickChar.length() > 3) {
                firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.INVALID_GUESS + "\n");
            }

            if (!compareChars(savingChar)) {

                maxRounds--;

                Graphics.incrementCounter();

                firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.WRONG_GUESS + "\n");

                gameOver(firstPlayer);

            } else {

                firstPlayer.getPlayerHandler().sendMessageToPlayer(Message.NICE_GUESS + "\n");

                gameWon(firstPlayer);
            }
        }
    }

    public void changeWordToChar(Player players) throws IOException {

        if (isChosen) {
            hiddenWord = new char[chosenWord.length()];

            for (int i = 0; i < chosenWord.length(); i++) {
                hiddenWord[i] += '-';
            }
            char[] a = hiddenWord;
            String str = new String(a);
            isChosen = false;
        }
    }

    public boolean compareChars(char character) {

        boolean foundChar = false;

        for (int i = 0; i < chosenWord.length(); i++) {
            if (character == chosenWord.charAt(i)) {
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

    public void printHiddenWord(Player player) {
        char[] a = hiddenWord;
        String str = new String(a);
        player.getPlayerHandler().sendMessageToPlayer(Message.GUESS_WORD + "\"" + str + "\"" + "\n");
    }

    public void playerMaker(int playerId, PlayerHandler playerHandler) {
        players[playerId] = new Player(playerHandler);
    }

    public void hangMaker(Player player) {
        for (Graphics picture : Graphics.values()) {
            if (Graphics.getCounter() == picture.getId()) {
                player.getPlayerHandler().sendMessageToPlayer(picture.getPicture() + "\n");
            }
        }
    }

    public void gameOver(Player player) throws IOException {
        if (maxRounds == 0) {
            player.getPlayerHandler().sendMessageToPlayer(Message.GAME_OVER + "\n");
            player.getPlayerHandler().sendMessageToPlayer(Graphics.STEP6.getPicture() + "\n");
            System.out.println(Message.GAME_OVER);
            start();
        }
    }

    public void gameWon(Player player) throws IOException {
        if (checkWordIsComplete()) {
            player.getPlayerHandler().sendMessageToPlayer(Message.YOU_WON + "\n");
            start();
        }
    }
}

