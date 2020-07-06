package org.academiadecodigo.gitbusters.Player;

import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.*;
import java.net.Socket;

public class Player_Handler implements Runnable {

    private final Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public Player_Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


        } catch (IOException e) {
            e.printStackTrace();
        }

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

        String newChar = "";

        try {
//            out.write(word);
//            out.flush();
            newChar = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(newChar);
        return newChar.toLowerCase();
    }

    public void sendMessageToPlayer(String message) {

        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }



    @Override
    public void run() {

    }
}