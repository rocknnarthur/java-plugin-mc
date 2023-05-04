package stunrayn.cl;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public final class GradesClass {

    private Scoreboard scoreboard;
    private final Grades plugin;
    private final Map<String, GradeList> gradePlayer = Maps.newHashMap();
    private final String prefix = "§8§l[§fGradesPlugin§8§l] ";

    private FileConfiguration config;
    private File file;

    public GradesClass(Grades plugin) {
        this.plugin = plugin;
        initConfig();
    }

    public final Grades getPlugin() {
        return plugin;
    }

    public String getPrefix(){
        return prefix;
    }

    public final Scoreboard getScoreboard() {
        return scoreboard;
    }

    public FileConfiguration getConfig(){
        return config;
    }

    private void initConfig() {
        File f = new File("plugins/Grades");
        if (!f.exists()) f.mkdirs();
        file = new File(f, "grades.yml");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException ioe){ ioe.printStackTrace();}
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void initScoreboard(){
        if(scoreboard != null) throw new UnsupportedOperationException("Scoreboard deja initialise.");
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        for(GradeList gradelist : GradeList.values()){
            Team team = scoreboard.registerNewTeam(gradelist.getName());
            team.setPrefix(gradelist.getPrefix());
            team.setSuffix(gradelist.getSuffix());

        }

    }
    public void loadPlayer(Player player){
        String uuid = player.getUniqueId().toString();
        if (gradePlayer.containsKey(uuid)) return;
        if (!config.contains(uuid)) changeGrade(uuid, GradeList.JOUEUR);

        gradePlayer.put(uuid, getGradesById(config.getInt(uuid)));
        scoreboard.getTeam(gradePlayer.get(uuid).getName()).addEntry(player.getName());
    }

    public void deletePlayer(Player player){
        if (!gradePlayer.containsKey(player.getUniqueId().toString())) return;
        gradePlayer.remove(player.getUniqueId().toString());
    }

    public GradeList getPlayerRank(Player player){
        if (!gradePlayer.containsKey(player.getUniqueId().toString())) loadPlayer(player);
        return gradePlayer.get(player.getUniqueId().toString());
    }

    public GradeList getGradesById(int id) {
        for (GradeList gradelist : GradeList.values()) {
            if (gradelist.getId() == id) return gradelist;
        }
        return GradeList.JOUEUR;
    }

    public void saveConfig() {
        try {
            config.save(file);
        }catch(IOException ioe) {ioe.printStackTrace();}
    }

    public boolean hasPower(Player player, int power){
        return (getPlayerRank(player).getPower() == power);

    }

    public boolean hasPowerSup(Player player, int power){
        return (getPlayerRank(player).getPower() > power);
    }

    public boolean hasPowerInf(Player player, int power){
        return (getPlayerRank(player).getPower() < power);
    }

    public void changeGrade(String uuid, GradeList gradeList){
        config.set(uuid, gradeList.getId());
        saveConfig();
    }

}


