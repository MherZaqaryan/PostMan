package me.MrIronMan.postman.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private final Player player;
    private boolean composeMode = false;
    private String playerToFind = null;
    private String mailToFind = null;
    private String playerToSend;

    public PlayerMenuUtility(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPlayerToSend() { return playerToSend; }

    public void setPlayerToSend(String player) {this.playerToSend = player;}

    public String getPlayerToFind() {
        return playerToFind;
    }

    public void setPlayerToFind(String playerToFind) {
        this.playerToFind = playerToFind;
    }

    public String getMailToFind() {
        return mailToFind;
    }

    public void setMailToFind(String mailToFind) {
        this.mailToFind = mailToFind;
    }

    public void setComposeMode(boolean b) {
        this.composeMode = b;
    }

    public boolean getComposeMode() {
        return this.composeMode;
    }
}
