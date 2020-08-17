package me.fifa12fcb.commands;

import me.fifa12fcb.lastinv;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class loadinv implements CommandExecutor {
    private lastinv plugin;

    public loadinv(lastinv plugin) {
        this.plugin = plugin;
        plugin.getCommand("loadinv").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
        if(!sender.hasPermission("loadinv.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou dont have permissions."));
            return false;
        }
        try {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if(p.getName().toLowerCase().equals(args[0].toLowerCase())) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLoading " + p.getName() + "&c's last saved inventory"));
                    if(plugin.items.containsKey(p.getUniqueId()) && (plugin.armor.containsKey(p.getUniqueId()))) {
                        plugin.RestoreInv(p);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cRestored " + p.getName() + "&c's inventory"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour inventory was loaded"));
                        plugin.items.remove(p.getUniqueId());
                        plugin.armor.remove(p.getUniqueId());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDeleted your last saved inventory"));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo inventory found"));
                    }
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
