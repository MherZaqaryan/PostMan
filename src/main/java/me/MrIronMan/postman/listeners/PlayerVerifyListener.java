package me.MrIronMan.postman.listeners;

import me.MrIronMan.postman.api.events.PlayerEmailVerifyEvent;
import me.MrIronMan.postman.configuration.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerVerifyListener implements Listener {

    @EventHandler
    public void onVerify(PlayerEmailVerifyEvent e) {
        Player player = e.getPlayer();
        if (!ConfigData.RUN_ON_VERIFY) return;
        ConfigData.VERIFY_COMMANDS.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", player.getName())));
    }

}
