package me.fifa12fcb;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class lastinv extends JavaPlugin implements CommandExecutor, Listener {
    private Plugin plugin;
    public Map<UUID, ItemStack[]> items = new HashMap<UUID, ItemStack[]>();
    public Map<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
    public Map<UUID, ItemStack[]> itemsdeath = new HashMap<UUID, ItemStack[]>();
    public Map<UUID, ItemStack[]> armordeath = new HashMap<UUID, ItemStack[]>();
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        plugin = this;
        new me.fifa12fcb.commands.saveinv(this);
        new me.fifa12fcb.commands.loadinv(this);
    }

    @EventHandler
    public void AtDeath(PlayerDeathEvent e) {
        StoreInvDeath(((Player)e.getEntity()).getPlayer());
    }

    public void StoreInv(Player player) {
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        items.put(uuid, contents);
        armor.put(uuid, armorContents);

        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void StoreInvDeath(Player player) {
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        itemsdeath.put(uuid, contents);
        armordeath.put(uuid, armorContents);

        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void RestoreInvDeath(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = itemsdeath.get(uuid);
        ItemStack[] armorContents = armordeath.get(uuid);

        if(contents != null) {
            player.getInventory().setContents(contents);
        } else {
            return;
        }
        if(armorContents != null){
            player.getInventory().setArmorContents(armorContents);
        }
        else{
            return;
        }
    }

    public void RestoreInv(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = items.get(uuid);
        ItemStack[] armorContents = armor.get(uuid);

        if(contents != null) {
            player.getInventory().setContents(contents);
        } else {
            return;
        }
        if(armorContents != null){
            player.getInventory().setArmorContents(armorContents);
        }
        else{
            return;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getServer().getPluginCommand("lastinv").setExecutor(this::onCommand);
        if(!sender.hasPermission("lastinv.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permissions!"));
            return false;
        }
        try {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.getName().toLowerCase().equals(args[0].toLowerCase())) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cRestoring " + p.getPlayer().getName() + "'s inventory"));
                    for(Player l: plugin.getServer().getOnlinePlayers()) {
                        if(l.hasPermission("lastinv.use")) {
                            l.sendMessage("");
                            l.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + " &crestored " + p.getName() + "&c's inventory"));
                            l.sendMessage("");
                        }
                    }
                    if(itemsdeath.containsKey(p.getUniqueId()) && armordeath.containsKey(p.getUniqueId())) {
                        RestoreInvDeath(p);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + sender.getName() + "&c restored your  inventory"));
                        itemsdeath.remove(p.getUniqueId());
                        armordeath.remove(p.getInventory());
                    }
                    else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&ccan't restore " + p.getName() + "&c's inventory"));
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

