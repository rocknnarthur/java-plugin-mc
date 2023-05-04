package fr.art333.survivalplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class joinCommand implements CommandExecutor {

    //Création de l'algorithme de la commande pour rejoindre le "Survival World"
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        World survivalWorld = Bukkit.getWorld("SurvivalCL");
        if(!(player.getWorld() == survivalWorld)) {

            World lobbyWorld = Bukkit.getWorld("Lobby");
            Player p = (Player) sender;
            if(!(p.getWorld() == lobbyWorld)){
                p.sendMessage("§cYou're not allowed to use this command in this world");
                return true;
            }

            // Recherche d'un fichier YML pour charger l'inventaire du joueur
            String playername = p.getName();
            File file = new File("plugins//SurvivalPL//Inventories//" + playername + ".yml");

            // Si le joueur possède un inventaire existant sauvegardé: son inventaire lui sera restitué
            if (file.exists()) {
                YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
                p.getInventory().clear();
                ItemStack[] contents = p.getInventory().getContents();
                List<?> list = inv.getList("Inventory");

                for (int i = 0; i < list.size(); i++) {
                    contents[i] = (ItemStack) list.get(i);
                }
                p.getInventory().setContents(contents);
                p.sendMessage("§aInventaire chargé avec succès");
                file.delete();

                Location survival = new Location(survivalWorld, 226.5, 72, 69.5, 0f, 0f);
                player.teleport(survival);
                player.setFoodLevel(20);
                player.setHealthScale(20);
                player.sendMessage("§ePour quitter le monde survie: §6§l/quitSurvival");

                // Sinon le joueur sera accueilli pour sa toute 1ère venue
            } else {
                Location survival = new Location(survivalWorld, 226.5, 72, 69.5, 0f, 0f);
                player.teleport(survival);
                player.setFoodLevel(20);
                player.setHealthScale(20);
                player.sendMessage("§eBienvenu à toi sur le monde §c§lSurvie §ede §dChômage Land !\n§ePour quitter le monde survie: §6§l/quitSurvival");
                System.out.println("Inventaire de §6" + playername + " pas encore enregistré");
            }
            return true;
        }else{
            player.sendMessage("§cVous êtes déjà dans le monde survie");
        }

        return true;
    }
}
