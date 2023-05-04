package fr.art333.scoreboardplugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreboardPlugin extends JavaPlugin implements Listener {

    private int taskID;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Scoreboard are enabled !");
        this.getServer().getPluginManager().registerEvents(this, this);

        if(Bukkit.getOnlinePlayers().isEmpty()){
            for (Player online : Bukkit.getOnlinePlayers()){
                createSBoard(online);
                start(online);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Scoreboard are disabled.");
    }

    // Affichage du scoreboard quand le joueur rejoint le serveur
    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        if (event.getPlayer().getWorld() == Bukkit.getWorld("SurvivalCL")){
            survivalSBoard(event.getPlayer());
        }

        if (event.getPlayer().getWorld() == Bukkit.getWorld("Lobby")){
            createSBoard(event.getPlayer());
            start(event.getPlayer());
        }

    }

    // Affichage d'un texte dans la liste des joueurs
    @EventHandler
    public void OnJoinList(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setPlayerListHeaderFooter(" §b§lBienvenue sur §d§lChômageLand ! \n "," \n §3§lAmusez vous bien ;) ");
    }

    // Changement du Scoreboard quand le joueur rejoint le BedWars
    @EventHandler
    public void OnJoinBW(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        LobbySBoard sboard = new LobbySBoard(player.getUniqueId());

        if(event.getView().getTitle().equalsIgnoreCase("Bed Wars: 1v1")){
            if(event.getCurrentItem().getType().equals(Material.QUARTZ_PILLAR)){
                if (sboard.hasID()){
                    sboard.stop();
                    bw1v1SBoard(player);
                }
            }
        }
    }

    // Changement du Scoreboard quand le joueur rejoint le "Survival World"
    @EventHandler
    public void OnJoinSurvival(PlayerChangedWorldEvent event){

        Player player = (Player) event;
        LobbySBoard sboard = new LobbySBoard(player.getUniqueId());

        if (event.getPlayer().getWorld() == Bukkit.getWorld("SurvivalCL")){
            if (sboard.hasID()){
                sboard.stop();
                survivalSBoard(player);
            }
        }
    }

    // Changement du Scoreboard quand le joueur quitte le "Survival World"
    @EventHandler
    public void OnLeaveSurvival(PlayerChangedWorldEvent event){

        Player player = (Player) event;

        if (event.getPlayer().getWorld() == Bukkit.getWorld("Lobby")){
            createSBoard(event.getPlayer());
            start(event.getPlayer());
        }

    }

    // Detection quand le joueur fait un clique droit avec le lit de "Retour" dans la main
    @EventHandler
    public void OnInteractLobbyBed(PlayerInteractEvent lobbyEvent) {

        Player player = lobbyEvent.getPlayer();
        Action action = lobbyEvent.getAction();
        ItemStack it = lobbyEvent.getItem();

        World lobbyWorld = Bukkit.getWorld("Lobby");
        Location lobby = new Location(lobbyWorld, 0.5, 130, 0.5, 0f, 0f);

        if(it == null) return;

        if(it.getType() == Material.RED_BED && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§cRetour au Lobby")){
            if(action == Action.RIGHT_CLICK_AIR){

                ItemStack menuNav = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta menuNav_meta = menuNav.getItemMeta();
                menuNav_meta.setDisplayName("§aMenu §7(Right Click)");
                ArrayList<String> menuNav_lore = new ArrayList<>();
                menuNav_lore.add("§7Right click to open the navigation menu");
                menuNav_meta.setLore(menuNav_lore);
                menuNav.setItemMeta(menuNav_meta);

                player.sendMessage("§aYou'll be teleport to the §bLobby §ain 3sec..");

                Bukkit.getScheduler().scheduleSyncDelayedTask(this, ()-> {
                    player.teleport(lobby);
                    createSBoard(player);
                    start(player);
                    player.getInventory().clear();
                    player.getInventory().setItem(4, menuNav);
                    player.updateInventory();
                }, 20L * 3);

            }
        }

    }

    // Suppression du scoreboard quand le joueur quitte le serveur
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        LobbySBoard sboard = new LobbySBoard(event.getPlayer().getUniqueId());
        if (sboard.hasID()) {
            sboard.stop();
        }
    }

    // Boucle de l'effet visuel du Titre du Scoreboard
    public void start(Player player) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            int count = 0;
            LobbySBoard board = new LobbySBoard(player.getUniqueId());

            @Override
            public void run() {
                if (!board.hasID()){
                    board.setID(taskID);
                }
                if (count == 14){
                    count = 0;
                }
                switch (count) {
                    case 0:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE LAND   "));
                        break;
                    case 1:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &e&lC&6&lHOMAGE LAND   "));
                        break;
                    case 2:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lC&e&lH&6&lOMAGE LAND   "));
                        break;
                    case 3:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCH&e&lO&6&lMAGE LAND   "));
                        break;
                    case 4:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHO&e&lM&6&lAGE LAND   "));
                        break;
                    case 5:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOM&e&lA&6&lGE LAND   "));
                        break;
                    case 6:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMA&e&lG&6&lE LAND   "));
                        break;
                    case 7:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAG&e&lE &6&lLAND   "));
                        break;
                    case 8:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE &e&lL&6&lAND   "));
                        break;
                    case 9:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE L&e&lA&6&lND   "));
                        break;
                    case 10:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE LA&e&lN&6&lD   "));
                        break;
                    case 11:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE LAN&e&lD   "));
                        break;
                    case 12:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &e&lCHOMAGE LAND   "));
                        break;
                    case 13:
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "   &6&lCHOMAGE &6&lLAND   "));
                        createSBoard(player);
                        break;
                }

                count++;
            }

        }, 0, 10);
    }

    // Création du scoreboard de l'accueil du serveur
    public void createSBoard(Player player){

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective menuObjective = scoreboard.registerNewObjective("menuSB", "dummy", "   §6§lCHOMAGE LAND   ");
        menuObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score scoreEmpty1 = menuObjective.getScore("§7  ");
        scoreEmpty1.setScore(5);
        Score scoreEmpty2 = menuObjective.getScore("§7 ");
        scoreEmpty2.setScore(3);
        Score scoreEmpty3 = menuObjective.getScore("§7");
        scoreEmpty3.setScore(1);

        Score scoreOnline = menuObjective.getScore("§2§lOnline: §a" + Bukkit.getOnlinePlayers().size());
        scoreOnline.setScore(4);

        Score scorePseudo = menuObjective.getScore("§3§lPseudo: §b" + player.getName());
        scorePseudo.setScore(2);

        Score scoreIP = menuObjective.getScore("§d§lplay.chomageland.fr");
        scoreIP.setScore(0);

        player.setScoreboard(scoreboard);
    }

    // Création du scoreboard du "Survival World"
    public void survivalSBoard(Player player){

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective survivalObjective = scoreboard.registerNewObjective("survivalSB", "dummy", "   §c§lSURVIVAL   ");
        survivalObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score scoreEmpty1 = survivalObjective.getScore("§7  ");
        scoreEmpty1.setScore(5);
        Score scoreEmpty2 = survivalObjective.getScore("§7 ");
        scoreEmpty2.setScore(3);
        Score scoreEmpty3 = survivalObjective.getScore("§7");
        scoreEmpty3.setScore(1);

        Score scorePseudo = survivalObjective.getScore("§e§lPseudo: §6" + player.getName());
        scorePseudo.setScore(4);

        Score scoreQuitInfo = survivalObjective.getScore("§4§lPour quitter: §c/quitSurvival");
        scoreQuitInfo.setScore(2);

        Score scoreIP = survivalObjective.getScore("§d§lplay.chomageland.fr");
        scoreIP.setScore(0);

        player.setScoreboard(scoreboard);
    }

    // Création du scoreboard de la file d'attente du BedWars
    public void bw1v1SBoard(Player player){

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective bw1v1Objective = scoreboard.registerNewObjective("bw1v1SB", "dummy", "§e§lBED WARS");
        bw1v1Objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score scoreEmpty1 = bw1v1Objective.getScore("§7  ");
        scoreEmpty1.setScore(5);
        Score scoreEmpty2 = bw1v1Objective.getScore("§7 ");
        scoreEmpty2.setScore(3);
        Score scoreEmpty3 = bw1v1Objective.getScore("§7");
        scoreEmpty3.setScore(1);

        Score scoreIP = bw1v1Objective.getScore("§d§lplay.chomageland.fr");
        scoreIP.setScore(0);

        World world = Bukkit.getWorld("Heaven");
        assert world != null;
        List<Player> players = world.getPlayers();
        int size = players.size();

        Score scoreNBRplayers = bw1v1Objective.getScore("§e" + size + "§a/2 Joueurs");
        scoreNBRplayers.setScore(2);

        Score scoreMapName = bw1v1Objective.getScore("§fMap: §a" + Bukkit.getWorld("Heaven").getName());
        scoreMapName.setScore(4);

        player.setScoreboard(scoreboard);

    }
}
