package me.MrIronMan.postman.configuration;

import me.MrIronMan.postman.util.Misc;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.entity.Player;

public class PluginData {

    public static final String 
            SUBCOMMAND_NOT_FOUND = "{prefix} §cSub-command not found type /Postman for help",
            USAGE_COMPOSE = "{prefix} §cUsage:§7 /PostMan Compose <Player|All> <FileName>",
            USAGE_UNREGISTER = "{prefix} §cUsage:§7 /PostMan Unregister <PlayerName>",
            USAGE_SET_SUBJECT = "{prefix} §cUsage:§7 /PostMan SetSubject <FileName> <Subject...>",
            POSTMAN_CMD_TITLE = "  §2§lPostMan Commands",
            POSTMAN_CMD = "§a§l§m----------------------------",
            NOT_PLAYER = "You can't run this command from console.",
            SEARCH_PLAYER = "{prefix} §aType player name you want to search!",
            SEARCH_MAIL = "{prefix} §aType mail file name you want to search!",
            EMAIL_COMPOSE_SENDING = "{prefix} §2Trying to send your compose...",
            EMAIL_COMPOSE_SENT = "{prefix} §aYour compose successfully sent.",
            PLAYER_IS_NOT_VERIFIED = "{prefix} §cPlayer §e{player} §cis not verified.",
            FILE_NOT_FOUND = "{prefix} §cFile §e{file}.html §cnot found in 'Mails' folder",
            VERIFY_FILE = "{prefix} §cSorry, but you cant send verification to player(s).",
            SUCCESSFULLY_UNREG = "{prefix} §aPlayer §e{player} §asuccessfully unregistered",
            SUBJECT_SET = "{prefix} §aSubject §e{subject} §asuccessfully set to file §e{file}!",
            PLAYER_NOT_FOUND = "{prefix} §c{player} §enot found.",
            NEW_UPDATE = "{prefix} §aThere is new update available §e{newVer}, §ayou are in §c{curVer}";

    public static void sendMainCmd(Player p) {
        p.sendMessage(TextUtil.colorize(PluginData.POSTMAN_CMD));
        p.sendMessage("");
        p.sendMessage(TextUtil.colorize(PluginData.POSTMAN_CMD_TITLE));
        p.sendMessage("");
        p.spigot().sendMessage(Misc.controlPanel);
        p.spigot().sendMessage(Misc.sendEmail);
        p.spigot().sendMessage(Misc.unRegisterEmail);
        p.spigot().sendMessage(Misc.setSubject);
        p.sendMessage("");
        p.sendMessage(TextUtil.colorize(PluginData.POSTMAN_CMD));
    }
    
}
