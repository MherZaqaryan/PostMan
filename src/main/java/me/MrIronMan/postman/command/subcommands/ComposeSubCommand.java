package me.MrIronMan.postman.command.subcommands;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.MailUtil;
import me.MrIronMan.postman.util.PermissionUtil;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComposeSubCommand extends SubCommand {

    @Override
    public String getName() {
        return "Compose";
    }

    @Override
    public String getPermission() {
        return PermissionUtil.COMPOSE;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(TextUtil.colorize(PluginData.USAGE_COMPOSE));
            return;
        }
        if (PostMan.getInstance().getFile(args[1] + ".html")) {
            player.sendMessage(TextUtil.colorize(PluginData.FILE_NOT_FOUND.replace("%file%", args[1])));
            return;
        }
        if (args[1].equalsIgnoreCase("verify")) {
            player.sendMessage(TextUtil.colorize(PluginData.VERIFY_FILE));
            return;
        }
        player.sendMessage(TextUtil.colorize(PluginData.EMAIL_COMPOSE_SENDING));
        if (args[0].equalsIgnoreCase("all")) {
            PostMan.getInstance().getSQLData().getPlayers().stream()
                    .filter(s -> PostMan.getInstance().getSQLData().isVerified(s))
                    .forEach(s -> MailUtil.send(s, PostMan.getInstance().getSQLData().getEmail(s), args[1] + ".html"));
        }
        else {
            String playerName = args[0];
            if (Bukkit.getOnlinePlayers().stream().noneMatch(p -> p.getName().equalsIgnoreCase(playerName))) {
                player.sendMessage(TextUtil.colorize(PluginData.PLAYER_NOT_FOUND.replace("{player}", playerName)));
                return;
            }
            if (PostMan.getInstance().getSQLData().isVerified(playerName)) {
                player.sendMessage(TextUtil.colorize(PluginData.PLAYER_IS_NOT_VERIFIED.replace("{player}", playerName)));
                return;
            }
            MailUtil.send(playerName, PostMan.getInstance().getSQLData().getEmail(playerName), args[1] + ".html");
        }
        player.sendMessage(TextUtil.colorize(PluginData.EMAIL_COMPOSE_SENT));
    }

}
