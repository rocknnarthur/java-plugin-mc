package stunrayn.cl;

import Listener.PlayerListener;
import commands.commandGrade;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Grades extends JavaPlugin {

    private GradesClass gradesClass;

    public final void onLoad(){
        gradesClass = new GradesClass(this);
    }

    public final void onEnable() {
        // Plugin startup logic
        System.out.println("Grades activés");

        gradesClass.initScoreboard();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(gradesClass), this);

        getCommand("grade").setExecutor(new commandGrade(gradesClass));


    }

    public final void onDisable() {
        // Plugin shutdown logic
        System.out.println("Grades désactivés");

    }
}
