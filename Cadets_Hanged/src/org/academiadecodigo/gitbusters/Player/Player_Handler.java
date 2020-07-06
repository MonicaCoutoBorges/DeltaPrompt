package org.academiadecodigo.gitbusters.Player;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.*;
import java.net.Socket;

public class Player_Handler implements Runnable {

    private final Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private PrintStream printStream;
    private Prompt prompt;


    public Player_Handler(Socket clientSocket, Prompt prompt,PrintStream printStream) {
        this.clientSocket = clientSocket;
        this.prompt = prompt;
        this.printStream = printStream;

    }

    public String pickWord(String word) {

        String newWord = "";

        try {
            out.write(word);
            out.flush();
            newWord = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newWord.toLowerCase();
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

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
    }
}