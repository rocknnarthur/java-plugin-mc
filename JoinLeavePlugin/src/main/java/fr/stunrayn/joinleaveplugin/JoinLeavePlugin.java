package fr.stunrayn.joinleaveplugin;

import events.Join;
import events.Leave;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinLeavePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Les messages de déco/reco sont activés");

        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Les messages de déco/reco sont désactivés");

    }
}