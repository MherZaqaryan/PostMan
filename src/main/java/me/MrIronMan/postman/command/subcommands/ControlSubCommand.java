package me.MrIronMan.postman.command.subcommands;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.api.commands.SubCommand;
import me.MrIronMan.postman.menu.menus.MainMenu;
import me.MrIronMan.postman.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ControlSubCommand extends SubCommand {

    @Override
    public String getName() {
        return "Control";
    }

    @Override
    public String getPermission() {
        return PermissionUtil.CONTROL;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        new MainMenu(PostMan.getPlayerMenuUtility(player)).open();
    }

}
