package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import stunrayn.cl.GradeList;
import stunrayn.cl.GradesClass;

public final class PlayerListener implements Listener {

    private final GradesClass gradesClass;

    public PlayerListener (GradesClass gradesClass){
        this.gradesClass = gradesClass;
    }

    @EventHandler
    private void playerJoin(PlayerJoinEvent pje){
        gradesClass.loadPlayer(pje.getPlayer());
        pje.getPlayer().setScoreboard(gradesClass.getScoreboard());

    }
    @EventHandler
    private void playerQuit(PlayerQuitEvent pqe){

        gradesClass.deletePlayer(pqe.getPlayer());
    }
    @EventHandler
    private void playerChat(AsyncPlayerChatEvent pce){
        GradeList gradeList = gradesClass.getPlayerRank(pce.getPlayer());
        pce.setFormat(gradeList.getPrefix()+pce.getPlayer().getName()+gradeList.getSuffix()+gradeList.getChatSeparator()+pce.getMessage());

    }
}
