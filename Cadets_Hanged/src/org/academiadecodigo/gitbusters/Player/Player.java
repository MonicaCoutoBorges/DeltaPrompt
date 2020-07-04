package org.academiadecodigo.gitbusters.Player;

public class Player {

    private Player_Handler playerHandler;
    private int id;

    public Player(int id, Player_Handler playerHandler) {

        this.id = id;
        this.playerHandler = playerHandler;
    }

    public int getId() {
        return id;
    }

    //method to send message to all player (when player leaves, all players will know)
    public void sendMessage(String message) {

        playerHandler.sendMessageToPlayer(message);

    }

    public Player_Handler getPlayerHandler() {
        return playerHandler;
    }
}
