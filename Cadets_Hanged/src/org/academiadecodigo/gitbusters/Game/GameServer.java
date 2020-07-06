package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.gitbusters.Player.PlayerHandler;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    private final static int port = 8000;
    private Prompt prompt;
    private final int nrOfPlayers = 1;
    private ExecutorService fixedPool;
    private final List<PlayerHandler> player_handlers = new ArrayList<PlayerHandler>();
    private ServerSocket serverSocket;
    private Game game;

    public GameServer(Game game) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.game = game;
        this.prompt = new Prompt(System.in, System.out);
        this.fixedPool = Executors.newFixedThreadPool(nrOfPlayers);
    }

    public static void main(String[] args) throws IOException {

        Game game = new Game();
        GameServer game_server = new GameServer(game);

        game_server.getHost();
        game_server.init();
    }

    public void init() throws IOException {

        System.out.println(Message.HIFENS);
        System.out.println(Message.SERVER_ON);

        while (true) {

            Socket clientSocket = serverSocket.accept();

            System.out.println(Message.HIFENS);
            System.out.println(Message.NEW_CONNECTION + clientSocket.getRemoteSocketAddress());
            System.out.println(Message.WELCOME);
            System.out.println(Message.HIFENS);

            PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
            prompt = new Prompt(clientSocket.getInputStream(), printStream);
            PlayerHandler playerHandler = new PlayerHandler(clientSocket, prompt, printStream);

            player_handlers.add(playerHandler);
            fixedPool.submit(playerHandler);

            game.playerMaker(1, playerHandler);

            playerHandler.sendMessageToPlayer(Message.WAITING_FOR_CONNECTION);

            gameStart(playerHandler);
        }
    }

    public void getHost() throws IOException {
        Socket hostSocket = serverSocket.accept();

        PrintStream printStream = new PrintStream(hostSocket.getOutputStream());
        Prompt hostPrompt = new Prompt(hostSocket.getInputStream(), printStream);

        String[] menuOptions = {"Play", "Exit"};
        MenuInputScanner scanner = new MenuInputScanner(menuOptions);
        scanner.setMessage(Message.DO_CHOICE);
        int menuOption = hostPrompt.getUserInput(scanner);

        switch (menuOption) {
            case 1:
                PlayerHandler playerHandler = new PlayerHandler(hostSocket, prompt, printStream);
                player_handlers.add(playerHandler);
                game.playerMaker(0, playerHandler);
                break;
            case 2:
                hostSocket.isClosed();
                break;
        }
    }

    public void gameStart(PlayerHandler playerHandler) throws IOException {
        if (player_handlers.size() == 2) {
            System.out.println(Message.GAME_WILL_START);
            playerHandler.sendMessageToPlayer(Message.GAME_WILL_START + " \n \n");
            game.start();
        }
    }
}
