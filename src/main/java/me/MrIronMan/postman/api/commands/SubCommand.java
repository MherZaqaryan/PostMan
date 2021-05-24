package me.MrIronMan.postman.api.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getPermission();
    public abstract void execute(CommandSender sender, String[] args);

}
