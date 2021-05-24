package me.MrIronMan.postman.command;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.configuration.ConfigData;
import me.MrIronMan.postman.configuration.MessagesData;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetEmailCommand extends BukkitCommand {

    public SetEmailCommand() {
        super(PostMan.getConfiguration().getString(ConfigData.COMMAND_SETEMAIL));
        this.setDescription("Set Email Command For Players");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PluginData.NOT_PLAYER);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(PermissionUtil.SETEMAIL)) {
            player.sendMessage(MessagesData.NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(MessagesData.USAGES_EMAIL);
            return true;
        }

        if (PostMan.isPending(player)) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_PENDING));
        }
        else if (PostMan.getInstance().getSQLData().isVerified(player.getName())) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_ALREADY_SET));
        }
        else if (PostMan.getInstance().getSQLData().isEmailOccupied(args[0])) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_OCCUPIED));
        }
        else if (!MailUtil.check(args[0])) {
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_INVALID_EMAIL));
        }
        else {
            String verificationCode = new AuthCodeUtil().getAuthCode();
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_SENDING));
            MailUtil.sendVerification(player.getName(), args[0], verificationCode);
            PostMan.addPending(player, verificationCode);
            PostMan.setEmailPending(player, args[0]);
            player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_SENT));
            new BukkitRunnable() {
                int i = ConfigData.VERIFY_CONFIRM_TIME;
                @Override
                public void run() {
                    if (!PostMan.isPending(player)) {
                        cancel();
                        return;
                    }
                    if (i == 0) {
                        cancel();
                        PostMan.removePending(player);
                        PostMan.removeEmailPending(player);
                        player.sendMessage(TextUtil.colorize(MessagesData.VERIFICATION_DENIED));
                        return;
                    }
                    i--;
                }
            }.runTaskTimer(PostMan.getInstance(), 0, 20);
        }
        return true;
    }

}
