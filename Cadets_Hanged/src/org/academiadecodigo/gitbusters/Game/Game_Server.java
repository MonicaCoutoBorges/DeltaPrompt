package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game_Server {

    private final static int port = 8000;
    private int playerCount = 0;
    private Prompt prompt;
    private final int nrOfPlayers = 1;
    private ExecutorService fixedPool;
    private final List<Player_Handler> player_handlers = new ArrayList<Player_Handler>();
    private  ServerSocket serverSocket;
    private Game game;

    public Game_Server(Game game) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game = game;
        prompt = new Prompt(System.in, System.out);
        fixedPool = Executors.newFixedThreadPool(nrOfPlayers);
    }
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        Game_Server game_server = new Game_Server(game);

        game_server.getHost();
        game_server.start();
    }
    public void start() throws IOException {

        System.out.println(Message.HIFFENS);
        System.out.println(Message.SERVER_ON);

        while (true) {

            Socket clientSocket = serverSocket.accept();


            System.out.println(Message.HIFFENS);
            System.out.println(Message.NEW_CONNECTION + clientSocket.getRemoteSocketAddress());
            System.out.println(Message.WELCOME);
            System.out.println(Message.HIFFENS);

            PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
            prompt = new Prompt(clientSocket.getInputStream(), printStream);
            Player_Handler playerHandler = new Player_Handler(clientSocket,prompt,printStream);
            player_handlers.add(playerHandler);

            fixedPool.submit(playerHandler);
            game.playerMaker(1, playerHandler);

            if (player_handlers.size() == 2) {
                System.out.println(Message.GAME_WILL_START);
                playerHandler.sendMessageToPlayer(Message.GAME_WILL_START + " \n \n");

                game.start();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void getHost() throws IOException {
        Socket hostSocket = serverSocket.accept();

        PrintStream printStream = new PrintStream(hostSocket.getOutputStream());
        Prompt hostPrompt = new Prompt(hostSocket.getInputStream(),printStream);

        String[] menuOptions = {"Play", "Exit"};
        MenuInputScanner scanner = new MenuInputScanner(menuOptions);
        scanner.setMessage("Choose what you wanna do");
        int menuOption = hostPrompt.getUserInput(scanner);

        switch (menuOption){
            case 1:
                Player_Handler playerHandler = new Player_Handler(hostSocket,prompt,printStream);
                player_handlers.add(playerHandler);
                game.playerMaker(0, playerHandler);
                break;
            case 2:
                hostSocket.isClosed();
                break;
        }
    }

}
