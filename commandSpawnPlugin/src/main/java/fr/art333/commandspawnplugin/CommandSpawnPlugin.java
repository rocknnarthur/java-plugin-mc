package fr.art333.commandspawnplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CommandSpawnPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("La commande /spawn est activée");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("La commande /spawn est désactivée");
    }

    // Mise en place de l'algorithme de la commande "/spawn"
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Détecte si le joueur est dans le "Survival World" car la commande n'est pas utilisable dans celui-ci
        World survivalWorld = Bukkit.getWorld("SurvivalCL");
        if(command.getName().equals("spawn")){
            if(sender instanceof Player) {
                Player player = (Player) sender;

                // S'il n'y est pas, alors le joueur se fera téléporté au Spawn du serveur
                if (!(player.getWorld() == survivalWorld)){

                    World lobby = Bukkit.getWorld("Lobby");
                    Location spawn = new Location(lobby, 0.5, 130, 0.5, 0f, 0f);
                    player.teleport(spawn);
                    player.sendMessage("§6You've been teleported to the §4Spawn");

                    player.getInventory().clear();

                    ItemStack menuNav = new ItemStack(Material.NETHER_STAR, 1);
                    ItemMeta menuNav_meta = menuNav.getItemMeta();
                    menuNav_meta.setDisplayName("§aMenu §7(Right Click)");
                    ArrayList<String> menuNav_lore = new ArrayList<>();
                    menuNav_lore.add("§7Right click to open the navigation menu");
                    menuNav_meta.setLore(menuNav_lore);
                    menuNav.setItemMeta(menuNav_meta);

                    player.getInventory().setItem(4, menuNav);

                    player.updateInventory();

                }else{
                    player.sendMessage("§cYou're not allowed to use this command in this world");
                }
            }

            return true;
        }
        return false;
    }
}
