package fr.art333.scoreboardplugin;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbySBoard {

    //Création des méthodes pour la boucle de l'effet visuel du Scoreboard
    private static Map<UUID, Integer> TASKS = new HashMap<UUID, Integer>();
    private final UUID uuid;

    public LobbySBoard(UUID uuid){
        this.uuid = uuid;
    }

    public void setID(int id){
        TASKS.put(uuid, id);
    }

    public int getID(){
        return TASKS.get(uuid);
    }

    public boolean hasID(){
        if(TASKS.containsKey(uuid)){
            return true;
        }
        return false;

    }

    // Mise en place de l'arrêt de la boucle
    public void stop() {
        Bukkit.getScheduler().cancelTask(TASKS.get(uuid));
        TASKS.remove(uuid);
    }

}
