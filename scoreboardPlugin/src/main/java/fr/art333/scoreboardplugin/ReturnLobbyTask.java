package fr.art333.scoreboardplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ReturnLobbyTask extends JavaPlugin implements Listener {

    private ScoreboardPlugin plugin;

    public ReturnLobbyTask(ScoreboardPlugin plugin){
        this.plugin = plugin;
    }

    // Détection du clique droit avec le lit de "Retour" & mise en place de la téléportation retardée
    @EventHandler
    public void OnInteractLobbyBed(PlayerInteractEvent lobbyEvent) throws InterruptedException {

        Player player = lobbyEvent.getPlayer();
        Action action = lobbyEvent.getAction();
        ItemStack it = lobbyEvent.getItem();

        World lobbyWorld = Bukkit.getWorld("Lobby");
        Location lobby = new Location(lobbyWorld, 0.5, 130, 0.5, 0f, 0f);

        if(it == null) return;

        if(it.getType() == Material.RED_BED && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§cRetour au Lobby")){
            if(action == Action.RIGHT_CLICK_AIR){
                player.sendMessage("§aYou'll be teleport to the §bLobby §ain 3sec..");

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()-> player.teleport(lobby), 20L * 3);

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
            }
        }

    }
}

