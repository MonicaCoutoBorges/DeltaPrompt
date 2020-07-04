package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.gitbusters.Player.Player;

import java.io.IOException;

public class Game {

    private final Player players[];
    private String guessingWord;
    private String hiddingWord;
    private Prompt prompt;
    private Game_Server gameServer;




    public Game(int players) {

        this.players = new Player[2];

        this.gameServer = new Game_Server();
    }


    public void start(){

        try {
            gameServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
