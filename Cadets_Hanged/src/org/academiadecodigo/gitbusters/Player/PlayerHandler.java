package org.academiadecodigo.gitbusters.Player;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private final Socket clientSocket;
    private PrintStream printStream;
    private Prompt prompt;

    public PlayerHandler(Socket clientSocket, Prompt prompt, PrintStream printStream) {
        this.clientSocket = clientSocket;
        this.prompt = prompt;
        this.printStream = printStream;
    }

    public String pickChar() {

        StringInputScanner newChar = new StringInputScanner();
        String playerChar = prompt.getUserInput(newChar);

        playerChar.charAt(0);

        System.out.println(newChar);
        return playerChar.toLowerCase();
    }

    public void sendMessageToPlayer(String message) {
        printStream.println(message + "\n");
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
    }
}