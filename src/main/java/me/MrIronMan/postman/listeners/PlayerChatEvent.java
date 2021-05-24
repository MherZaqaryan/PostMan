package me.MrIronMan.postman.listeners;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.menu.menus.MailsMenu;
import me.MrIronMan.postman.menu.menus.PlayersMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerChatEvent implements Listener {

    public static HashMap<UUID, String> playersSearchMap = new HashMap<>();
    public static HashMap<UUID, String> mailsSearchMap = new HashMap<>();

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();

        if (playersSearchMap.containsKey(player.getUniqueId())) {
            e.setCancelled(true);
            PostMan.getPlayerMenuUtility(player).setPlayerToFind(message);
            new PlayersMenu(PostMan.getPlayerMenuUtility(player)).open();
            playersSearchMap.remove(player.getUniqueId());
            PostMan.getPlayerMenuUtility(player).setPlayerToFind(null);
        }

        if (mailsSearchMap.containsKey(player.getUniqueId())) {
            e.setCancelled(true);
            PostMan.getPlayerMenuUtility(player).setMailToFind(message);
            new MailsMenu(PostMan.getPlayerMenuUtility(player)).open();
            mailsSearchMap.remove(player.getUniqueId());
            PostMan.getPlayerMenuUtility(player).setMailToFind(null);
        }
    }

}
