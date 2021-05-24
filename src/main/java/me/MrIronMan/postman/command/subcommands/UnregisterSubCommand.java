package me.MrIronMan.postman.command.subcommands;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterSubCommand extends SubCommand {

    @Override
    public String getName() {
        return "UnRegister";
    }

    @Override
    public String getPermission() {
        return PermissionUtil.UNREGISTER;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(PluginData.USAGE_UNREGISTER);
            return;
        }
        String playerName = args[0];
        if (PostMan.getInstance().getSQLData().isVerified(playerName)) {
            player.sendMessage(PluginData.PLAYER_IS_NOT_VERIFIED.replace("{player}", playerName));
            return;
        }
        PostMan.getInstance().getSQLData().setEmail(playerName, null);
        player.sendMessage(PluginData.SUCCESSFULLY_UNREG.replace("{player}", playerName));
    }

}
