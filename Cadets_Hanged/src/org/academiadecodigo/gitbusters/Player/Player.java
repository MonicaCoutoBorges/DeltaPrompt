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

    // tirei o id daqui, nao precisamos dele
    public Player(Player_Handler playerHandler) {
        this.playerHandler = playerHandler;
    }

    //metodo para enviar menssagem ler o que vou meter em baixo com muita atençao
    //ESTE METODO SO É USADO PARA MANDAR MENSSAGEM PARA A POWER SHELL,
    //PARA MANDAR MENSSAGEM PARA O INTELLIJ PODIAMOS USAR SEMPRE A PROMPT!!!
    //atençao, é só uma opinião
    public void sendMessage(String string) throws IOException {
        playerHandler.getOut().write(string);
        playerHandler.getOut().flush();
    }

    public Player_Handler getPlayerHandler() {
        return playerHandler;
    }


    public String pickLetter(){

        return playerHandler.pickChar(Message.PICK_CHAR + "/n");
    }
}
