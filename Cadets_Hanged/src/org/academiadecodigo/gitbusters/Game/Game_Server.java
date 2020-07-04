package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game_Server {

    private final static int port = 8000;

    private int playerCount = 0;
    private String playerName;
    private Prompt prompt;
    private final int nrOfPlayers = 2;
    private static int ACTIVEPLAYERS = 1;
    private ExecutorService fixedPool;

    public Game_Server() {
        prompt = new Prompt(System.in, System.out);
        fixedPool = Executors.newFixedThreadPool(nrOfPlayers);
    }

    public static void main(String[] args) throws IOException {
        Game_Server game_server = new Game_Server();
        game_server.start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = null;

        Game game = new Game(nrOfPlayers);

        serverSocket = new ServerSocket(port);
        System.out.println(Message.SERVER_ON);


        //Waits for connection till client

        while (ACTIVEPLAYERS <= 2) {

            Socket clientSocket = serverSocket.accept();

            Player_Handler playerHandler = new Player_Handler(clientSocket);

            Player player = new Player(ACTIVEPLAYERS, playerHandler);

            fixedPool.submit(playerHandler);

            ACTIVEPLAYERS++;

            player.getPlayerHandler().getOut().write(Message.NEW_CONNECTION + clientSocket.getInetAddress().getHostAddress() + "\n");
            player.getPlayerHandler().getOut().flush();

            if (player.getId() == 1) {

                player.getPlayerHandler().getOut().write(Message.WELCOME + "\n");
                player.getPlayerHandler().getOut().flush();

                player.getPlayerHandler().getOut().write(Message.WAITING_FOR_PLAYER + "\n");
                player.getPlayerHandler().getOut().flush();
            }
            if (player.getId() == 2) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.start();
            }
        }
        serverSocket.close();

        ACTIVEPLAYERS = 1;
    }
}
