package me.MrIronMan.postman.listeners;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.configuration.ConfigData;
import me.MrIronMan.postman.util.Misc;
import me.MrIronMan.postman.util.PermissionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Connections implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PostMan.getInstance().getSQLData().addPlayer(player);
        if (!ConfigData.CHECK_UPDATES) return;
        if (!player.hasPermission(PermissionUtil.UPDATE_NOTIFICATION)) return;
        if (!PostMan.getInstance().getUpdateChecker().isAvailable()) return;
        player.spigot().sendMessage(Misc.update());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerChatEvent.playersSearchMap.remove(player.getUniqueId());
        PlayerChatEvent.mailsSearchMap.remove(player.getUniqueId());
    }

}
