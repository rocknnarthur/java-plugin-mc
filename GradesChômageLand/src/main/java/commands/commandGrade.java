package commands;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import stunrayn.cl.GradeList;
import stunrayn.cl.GradesClass;

import java.util.List;

public final class commandGrade implements CommandExecutor, TabCompleter {

    private final GradesClass gradesClass;

    public commandGrade(GradesClass gradesClass) {
        this.gradesClass = gradesClass;
    }

    // /rank <player> <GradeName/id>
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
            if (gradesClass.hasPowerInf((Player)sender, 90))
                return sendMessage(sender, "§cYou cannot use this command");


        if (args.length < 2) return sendMessage(sender, "§c/rank <player> <GradeName/id>");

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if(target == null) return sendMessage(sender, "§cLe joueur n'a pas été trouvé");
        String uuid = target.getUniqueId().toString();
        if (!gradesClass.getConfig().contains(uuid)) return sendMessage(sender, "§cLe joueur n'est pas connecté");

        GradeList gradeList = null;

        try {
            gradeList = gradesClass.getGradesById(Integer.parseInt(args[1]));
        }catch (NumberFormatException nbe){
            try {
                gradeList = GradeList.valueOf(args[1].toUpperCase());
            }catch (Exception e){
                return sendMessage(sender, "§cLe grade n'a pas été trouvé");
            }

        }

        gradesClass.changeGrade(uuid, gradeList);
        if (target.isOnline()){
            sendMessage(target.getPlayer(), "§aVotre grade a été modifié");
            gradesClass.deletePlayer(target.getPlayer());
            gradesClass.loadPlayer(target.getPlayer());
        }
        return sendMessage(sender, "§6§l" + target.getName() + "§a a bien obtenu le grade §d§l" + gradeList.getName().toLowerCase());
    }

    public boolean sendMessage(CommandSender sender, String msg){
        sender.sendMessage(gradesClass.getPrefix() + msg);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabComplete = Lists.newArrayList();

        if (args.length == 2){
            for (GradeList gradeList : GradeList.values()){
                if (gradeList.getName().toLowerCase().startsWith(args[1].toLowerCase())) tabComplete.add(gradeList.getName());
            }

            return tabComplete;
        }

        return null;
    }
}
