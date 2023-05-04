package fr.art333.taskschedulerplugin.tasks;

import fr.art333.taskschedulerplugin.TaskSchedulerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class returnLobbyTask extends BukkitRunnable {

    TaskSchedulerPlugin plugin;

    public returnLobbyTask(TaskSchedulerPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {

        World lobbyWorld = Bukkit.getWorld("Lobby");
        Location lobby = new Location(lobbyWorld, 0.5, 130, 0.5, 0f, 0f);
        player.teleport(lobby);

    }
}
