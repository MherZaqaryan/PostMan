package me.MrIronMan.postman.command;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.events.PlayerEmailVerifyEvent;
import me.MrIronMan.postman.configuration.ConfigData;
import me.MrIronMan.postman.configuration.MessagesData;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.PermissionUtil;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.Bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class VerifyCommand extends BukkitCommand {

    public VerifyCommand() {
        super(PostMan.getConfiguration().getString(ConfigData.COMMAND_VERIFY));
        this.setDescription("Verify Command For Players");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PluginData.NOT_PLAYER);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(PermissionUtil.VERIFY)) {
            player.sendMessage(TextUtil.colorize(MessagesData.NO_PERMISSION));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(TextUtil.colorize(MessagesData.USAGES_VERIFY));
            return false;
        }

        if (PostMan.getInstance().getSQLData().isVerified(player.getName())) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_ALREADY_VERIFIED));
        }
        else if (!PostMan.isPending(player)) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_NOT_PENDING));
        }
        else {
            if (!args[0].equals(PostMan.getAuthCode(player))) {
                player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_WRONG_CODE));
                return false;
            }
            PostMan.getInstance().getSQLData().setEmail(player.getName(), PostMan.getEmailPending(player));
            Bukkit.getPluginManager().callEvent(new PlayerEmailVerifyEvent(player, PostMan.getAuthCode(player)));
            PostMan.removeEmailPending(player);
            PostMan.removePending(player);
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_FAILED));
        }

        return false;
    }
}
