package me.fifa12fcb.commands;

import me.fifa12fcb.lastinv;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class saveinv implements CommandExecutor {
    private lastinv plugin;

    public saveinv(lastinv plugin) {
        this.plugin = plugin;
        plugin.getCommand("saveinv").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("saveinv.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permissions"));
            return false;
        }
        try {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if(p.getName().toLowerCase().equals(args[0].toLowerCase())) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSaving " + p.getName() + "&c's inventory"));
                    plugin.StoreInv(p);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSuccessfully saved " + p.getName() + "&c's inventory"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour inventory was stored"));
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to select a online player"));
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must select a player"));
            return true;
        }
        }
}