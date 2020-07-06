package org.academiadecodigo.gitbusters.Player;

public class Player {

    private PlayerHandler playerHandler;

    public Player(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void sendMessage(String string) {
        playerHandler.sendMessageToPlayer(string);
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}
