package fr.art333.menuplugin;

import fr.art333.menuplugin.commands.menuCommand;
import fr.art333.menuplugin.events.ClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MenuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Les menus sont activés");

        // Création de la commande spécifique au Plugin "Menu"
        getCommand("menu").setExecutor(new menuCommand());

        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Les menus sont désactivés");
    }
}
