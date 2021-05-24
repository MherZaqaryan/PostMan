package me.MrIronMan.postman.util;

import me.MrIronMan.postman.configuration.MessagesData;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String colorize(String s) {
        return s == null ? " " : ChatColor.translateAlternateColorCodes('&', s.replace("{prefix}", MessagesData.PREFIX));
    }

    public static String strip(String s) {
        return s == null ? " " : ChatColor.stripColor(s);
    }

    public static List<String> colorize(List<String> list) {
        List<String> newList = new ArrayList<>();
        list.forEach(s -> newList.add(colorize(s)));
        return newList;
    }

}
