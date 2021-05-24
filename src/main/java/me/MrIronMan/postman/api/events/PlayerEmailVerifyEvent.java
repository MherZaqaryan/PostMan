package me.MrIronMan.postman.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEmailVerifyEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private String authCode;

    public PlayerEmailVerifyEvent(Player player, String authCode) {
        this.player = player;
        this.authCode = authCode;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAuthCode() {
        return authCode;
    }

}
