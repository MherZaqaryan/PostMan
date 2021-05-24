package me.MrIronMan.postman.util;

import me.MrIronMan.postman.PostMan;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;

public class ReflectUtils {

    public ReflectUtils() {
        PostMan.getInstance().getLogger().info("Loading support for version: " + getVersion());
    }

    public String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public void registerCommand(String cmd, BukkitCommand bukkitCommand) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(cmd, bukkitCommand);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
