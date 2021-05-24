package me.MrIronMan.postman.command.subcommands;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.PermissionUtil;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSubjectSubCommand extends SubCommand {

    @Override
    public String getName() {
        return "SetSubject";
    }

    @Override
    public String getPermission() {
        return PermissionUtil.SET_SUBJECT;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(TextUtil.colorize(PluginData.USAGE_SET_SUBJECT));
        }
        if (!PostMan.getInstance().getFile(args[1] + ".html")) {
            player.sendMessage(PluginData.FILE_NOT_FOUND.replace("{file}", args[0]));
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < args.length; i++) builder.append(args[i]).append(" ");
        String subject = builder.toString();
        PostMan.getInstance().getSQLData().setSubject(args[0] + ".html", subject);
        player.sendMessage(PluginData.SUBJECT_SET.replace("{subject}", subject).replace("%file%", args[1]+".html"));
    }

}
