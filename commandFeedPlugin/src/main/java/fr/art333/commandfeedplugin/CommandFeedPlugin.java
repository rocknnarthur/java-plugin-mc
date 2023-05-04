package fr.art333.commandfeedplugin;

import fr.art333.commandfeedplugin.commands.feedCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandFeedPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("La commande /feed est activée");

        // Création de la commande spécifique au Plugin "Feed"
        getCommand("feed").setExecutor(new feedCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("La commande /feed est désactivée");
    }
}
