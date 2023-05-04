package fr.art333.survivalplugin;

import fr.art333.survivalplugin.quitCommand;
import fr.art333.survivalplugin.joinCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Survival is working");

        //Création de commandes spécifiques au Plugin pour le "Survival World"
        getCommand("quitSurvival").setExecutor(new quitCommand());
        getCommand("joinSurvival").setExecutor(new joinCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Survival is not even working");
    }

}
