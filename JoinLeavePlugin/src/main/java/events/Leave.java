package events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    // Détection du joueur qui quitte le serveur & annonce sa déconnexion du serveur dans le tchat
    @EventHandler
    void onPlayerLeave(PlayerQuitEvent event){

        Player player = event.getPlayer();
        event.setQuitMessage("§c[-] " + ChatColor.RED + player.getDisplayName() + ChatColor.YELLOW + " a quitté le serveur");

    }


}
