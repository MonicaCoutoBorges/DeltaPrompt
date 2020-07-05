package org.academiadecodigo.gitbusters.Game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.gitbusters.Player.Player;
import org.academiadecodigo.gitbusters.Player.Player_Handler;
import org.academiadecodigo.gitbusters.Utility.Message;

import java.io.IOException;
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
    private String playerName;
    private Prompt prompt;
    //mudei o nº de players porque o intellij ja é um player, (ver threads)
    private final int nrOfPlayers = 1;
    private ExecutorService fixedPool;
    //crio aqui uma arraylista do tipo player handler
    private final List<Player_Handler> player_handlers = new ArrayList<Player_Handler>();

    public Game_Server() {

        //abre a prompt e as threads,
        prompt = new Prompt(System.in, System.out);
        //nºde players é 1, o intellij já está a ser executado na main thread, só o powershell é que precisa de outra thread
        fixedPool = Executors.newFixedThreadPool(nrOfPlayers);
    }

    public static void main(String[] args) throws IOException {

        Game_Server game_server = new Game_Server();
        game_server.start();
    }

    public void start() throws IOException {

        ServerSocket serverSocket = null;
        Game game = new Game();
        serverSocket = new ServerSocket(port);
        System.out.println(Message.HIFFENS);
        System.out.println(Message.SERVER_ON);
        //Waits for connection till client
        //podemos mudar o  true para algo mais bonito depois, mas já não tenho cabeça

        while (true) {

            Socket clientSocket = serverSocket.accept();

            System.out.println(Message.HIFFENS);
            System.out.println(Message.NEW_CONNECTION + clientSocket.getRemoteSocketAddress());
            System.out.println(Message.WELCOME);
            System.out.println(Message.HIFFENS);

            // !!!! Crio os player handlers e damos add deles na Array list ( já lá vamos, venham comigo!!)
            Player_Handler playerHandler = new Player_Handler(clientSocket);
            player_handlers.add(playerHandler);

            //parabens , o player power shell ganhou a sua thread
            fixedPool.submit(playerHandler);

            //CHAMO O METODO CRATE PLAYER, vai criar o player e recebe a hander como argumento
            game.createPlayer(nrOfPlayers, playerHandler);

            //se o  tamanho da nossa array de handlers for = a 1, o jogo realmente começa,
            //se o tamanho da array for 1, quer dizer k ja esta la o handler da power shell e o do intellij
            if (player_handlers.size() == 1) {
                System.out.println(Message.GAME_WILL_START);
                playerHandler.getOut().write(Message.GAME_WILL_START + " \n \n");
                playerHandler.getOut().flush();
                game.start();
            }
            //ver o que fazer com isto depois
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
