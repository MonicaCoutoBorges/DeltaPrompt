package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.gitbusters.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private final Player players[];

    private ArrayList<Character> characters = new ArrayList<>();

    private String guessingWord;
    private String hiddingWord;
    private Prompt prompt;
    private Game_Server gameServer;





    public Game(int players) {

        this.players = new Player[2];

        this.gameServer = new Game_Server();
    }


    public void start(){


        System.out.println("aaaaaaaaaaaaaaaaaaa");

       /* try {
            gameServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } */


    }

    public void sendMessageToAll(String message){

        for(Player player : players){
           //por aqui uma condição que faça com que não envie sms para o player que enviou
            player.sendMessage(message);
        }


    }

}
