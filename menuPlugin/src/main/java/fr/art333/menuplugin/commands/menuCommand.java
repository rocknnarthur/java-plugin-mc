package fr.art333.menuplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;

public class menuCommand implements CommandExecutor {

// Commande "/menu" = création du menu principal + ouvre celui-ci pour le joueur executeur
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            Inventory menu = Bukkit.createInventory(player, 9, "§5ChômageLand Menu");

            ItemStack air = new ItemStack(Material.AIR);
            ItemStack stats = new ItemStack(Material.BOOK);
            ItemStack achievements = new ItemStack(Material.TOTEM_OF_UNDYING);
            ItemStack play = new ItemStack(Material.SUNFLOWER);
            ItemStack shop = new ItemStack(Material.GOLD_INGOT);
            ItemStack cosmetics = new ItemStack(Material.DIAMOND_BLOCK);

            ItemMeta stats_meta = stats.getItemMeta();
            stats_meta.setDisplayName("§aStats");
            ArrayList<String> stats_lore = new ArrayList<>();
            stats_lore.add("§7stats description");
            stats_meta.setLore(stats_lore);
            stats.setItemMeta(stats_meta);

            ItemMeta achievements_meta = achievements.getItemMeta();
            achievements_meta.setDisplayName("§aAchievements");
            ArrayList<String> achievements_lore = new ArrayList<>();
            achievements_lore.add("§7achievements description");
            achievements_meta.setLore(achievements_lore);
            achievements.setItemMeta(achievements_meta);

            ItemMeta play_meta = play.getItemMeta();
            play_meta.setDisplayName("§aJouer");
            ArrayList<String> play_lore = new ArrayList<>();
            play_lore.add("§7play description");
            play_meta.setLore(play_lore);
            play.setItemMeta(play_meta);

            ItemMeta shop_meta = shop.getItemMeta();
            shop_meta.setDisplayName("§aShop");
            ArrayList<String> shop_lore = new ArrayList<>();
            shop_lore.add("§7shop description");
            shop_meta.setLore(shop_lore);
            shop.setItemMeta(shop_meta);

            ItemMeta cosmetics_meta = cosmetics.getItemMeta();
            cosmetics_meta.setDisplayName("§aCosmétiques");
            ArrayList<String> cosmetics_lore = new ArrayList<>();
            cosmetics_lore.add("§7cosmetics description");
            cosmetics_meta.setLore(cosmetics_lore);
            cosmetics.setItemMeta(cosmetics_meta);

            ItemStack[] menu_items = {stats, air, achievements, air, play, air, shop, air, cosmetics};
            menu.setContents(menu_items);
            player.openInventory(menu);

        }else{
            System.out.println("§cYou are not allowed to use this command");
        }


        return true;
    }
}
