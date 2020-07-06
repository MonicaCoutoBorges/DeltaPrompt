package org.academiadecodigo.gitbusters.Player;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.net.Socket;

public class Player {

    private Prompt prompt;
    private Player_Handler playerHandler;

    public Player(Player_Handler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void sendMessage(String string) throws IOException {
        playerHandler.sendMessageToPlayer(string);
    }

    public void sendChar(char character) throws IOException {
        playerHandler.getOut().write(character);
        playerHandler.getOut().flush();
    }

    public Player_Handler getPlayerHandler() {
        return playerHandler;
    }

    public String pickLetter(){
        return playerHandler.pickChar();
    }
}
