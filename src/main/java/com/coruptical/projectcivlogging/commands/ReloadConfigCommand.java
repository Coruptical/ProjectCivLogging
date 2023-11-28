package com.coruptical.projectcivlogging.commands;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadConfigCommand implements CommandExecutor {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player || commandSender instanceof ConsoleCommandSender){
            if (!commandSender.hasPermission(plugin.getAdminPermission()))
                commandSender.sendMessage(plugin.getNoPermissionMessage());

            if (commandSender instanceof Player){
                Player p = (Player) commandSender;
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&7[Project Civ Logging&7] &aSuccessfully reloaded config."));
            }else {
                commandSender.sendMessage("Reloaded config.yml");
            }

            plugin.reloadConfig();
        }
        return true;
    }
}
