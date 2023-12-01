package com.coruptical.projectcivlogging.commands;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlaytimeCommand implements CommandExecutor, TabCompleter {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("You must be a player to execute this command!");
            return true;
        }

        Player p = (Player) commandSender;

        if (!p.hasPermission(plugin.getAdminPermission())){
            p.sendMessage(plugin.getNoPermissionMessage());
            return true;
        }

        if (strings.length == 0){
            long playtime = plugin.getPlaytimeMap().get(p);
            String formatted = Utils.calculateTime(playtime);

            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&bYour playtime: &f" + formatted));
        }else {
            String playerName = strings[0];
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerName);
                if (!player.isOnline()){
                    String uuid = player.getUniqueId().toString();

                    if (!plugin.getPlaytimeHandler().getConfig().contains(uuid)){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&cThis player hasn't joined before"));
                        return;
                    }

                    long playtime = plugin.getPlaytimeHandler().getConfig().getInt(uuid);
                    String formatted = Utils.calculateTime(playtime);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&b" + strings[0].toLowerCase() + "'s playtime: &f" + formatted));
                }else{
                    Player requestedPlayer = plugin.getServer().getPlayer(strings[0].toLowerCase());
                    long playtime = plugin.getPlaytimeMap().get(requestedPlayer);
                    String formatted = Utils.calculateTime(playtime);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&b" + strings[0].toLowerCase() + "'s playtime: &f" + formatted));
                }

            });
        }



        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();

        if (strings.length == 0) return list;

        if (strings.length == 1 && strings[0].isEmpty()){
            for (Player p : plugin.getServer().getOnlinePlayers()){
                list.add(p.getName());
            }
        }

        if (strings.length == 1 && !strings[0].isEmpty()){
            for (Player p : plugin.getServer().getOnlinePlayers()){
                if (p.getName().toLowerCase().startsWith(strings[0].toLowerCase())) list.add(p.getName());
            }
        }

        return list;
    }
}
