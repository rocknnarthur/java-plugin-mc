package fr.art333.survivalplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class quitCommand implements CommandExecutor {

    //Création de l'algorithme de la commande pour quitter le "Survival World"
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        World survivalWorld = Bukkit.getWorld("SurvivalCL");
        if (player.getWorld() == survivalWorld){

            this.checkOrder();

            Player p = (Player) sender;

            // Création d'un fichier YML pour sauvegarder l'inventaire du joueur
            ArrayList<ItemStack> list = new ArrayList<>();
            String playername = p.getName();
            File file = new File("plugins//SurvivalPL//Inventories//"+playername+".yml");

            // Si le joueur possède un inventaire existant sauvegardé: un nouveau fichier remplacera l'existant
            if (!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException e){
                    p.sendMessage("§cImpossible de trouver le chemin spécifié");
                }
                YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
                ItemStack[] contents = p.getInventory().getContents();
                for (int i = 0; i < contents.length; i++){
                    ItemStack item = contents[i];
                    if (!(item == null)){
                        list.add(item);
                    }
                }
                inv.set("Inventory", list);
                try {
                    inv.save(file);
                } catch (IOException e) {
                    p.sendMessage("§cImpossible de trouver le chemin spécifié");
                }
                p.getInventory().clear();
                p.sendMessage("§aInventaire sauvegardé avec succès");

                // Après la sauvegarde de son inventaire, le joueur se fera téléporté au Lobby du serveur
                World lobby = Bukkit.getWorld("Lobby");
                Location spawn = new Location(lobby, 0.5, 130, 0.5, 0f, 0f);
                player.teleport(spawn);

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
                System.out.println("Inventaire de §6"+playername+" déjà sauvegardé");
            }

            return true;

        }else{
            player.sendMessage("§cVous n'êtes pas dans le monde survie");
        }
        return true;
    }

    // Chemin spécifié des inventaires sauvegardés
    public void checkOrder(){
        File file = new File("plugins//SurvivalPL//Inventories");
        if (!file.exists()){
            file.mkdir();
        }
    }

}
