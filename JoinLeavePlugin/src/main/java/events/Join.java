package events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    // Détection du joueur qui rejoint le serveur & annonce sa connexion sur le serveur dans le tchat
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event){

        World survivalWorld = Bukkit.getWorld("SurvivalCL");
        Player player = event.getPlayer();
        event.setJoinMessage("§a[+] " + ChatColor.GREEN + player.getDisplayName() + ChatColor.YELLOW + " a rejoint le serveur");

        if(!(event.getPlayer().getWorld() == survivalWorld)){
            player.sendTitle("§6§lCHÔMAGE LAND","§b§lby FC Chômage",0,60,20);

            World lobby = Bukkit.getWorld("Lobby");
            Location spawn = new Location(lobby, 0.5, 130, 0.5, 0f, 0f);
            player.teleport(spawn);

        }

    }


}