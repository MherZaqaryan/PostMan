package me.MrIronMan.postman.command;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.configuration.MessagesData;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.*;
import org.bukkit.command.CommandSender;

import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PostManCommand extends BukkitCommand {

    public PostManCommand() {
        super("postman");
        this.setDescription("PostMan main command, run to see all list of commands.");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PluginData.NOT_PLAYER);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!player.hasPermission(PermissionUtil.HELP)) {
                player.sendMessage(TextUtil.colorize(MessagesData.NO_PERMISSION));
                return true;
            }
            PluginData.sendMainCmd(player);
            return true;
        }

        if (PostMan.subCommands.stream().noneMatch(c -> c.getName().equalsIgnoreCase(args[0]))) {
            player.sendMessage(TextUtil.colorize(PluginData.SUBCOMMAND_NOT_FOUND));
            return true;
        }

        SubCommand subCommand = PostMan.subCommands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().get();

        if (!sender.hasPermission(subCommand.getPermission())) {
            player.sendMessage(TextUtil.colorize(MessagesData.NO_PERMISSION));
            return true;
        }

        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.execute(sender, newArgs);
        return true;
    }

}
