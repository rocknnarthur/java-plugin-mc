package fr.art333.menuplugin.events;

import fr.art333.menuplugin.MenuPlugin;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.Thread.*;

public class ClickEvent implements Listener {

// Give d'une nether star "Menu" à la connexion du joueur
    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        if (player.getWorld() == Bukkit.getWorld("Lobby")){

            player.getInventory().clear();

            ItemStack menuNav = new ItemStack(Material.NETHER_STAR, 1);
            ItemMeta menuNav_meta = menuNav.getItemMeta();
            menuNav_meta.setDisplayName("§aMenu §7(Right Click)");
            ArrayList<String> menuNav_lore = new ArrayList<>();
            menuNav_lore.add("§7Right click to open the navigation menu");
            menuNav_meta.setLore(menuNav_lore);
            menuNav.setItemMeta(menuNav_meta);

            player.getInventory().setItem(4, menuNav);

            player.updateInventory();
        }
    }
// Clique droit avec la nether star = accès au menu principal
    @EventHandler
    public void OnInteractMenu(PlayerInteractEvent event){

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack it = event.getItem();

        if(it == null) return;

        if(it.getType() == Material.NETHER_STAR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu §7(Right Click)")){
            if(action == Action.RIGHT_CLICK_AIR){
                player.performCommand("menu");
            }
        }
    }
// Empêche le déplacement de la nether star "Menu"
    @EventHandler
    public void antiMoveMenuItemEvent(InventoryClickEvent eventAnti){

        ItemStack it = eventAnti.getCurrentItem();

        if (eventAnti.getView().getBottomInventory().contains(Material.NETHER_STAR)){
            if (it.getType() == Material.NETHER_STAR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu §7(Right Click)")){
                eventAnti.setCancelled(true);
            }
        }
    }
// Empêche le drop de la nether star "Menu"
    @EventHandler
    public void OnDropMenu(PlayerDropItemEvent eventDrop){

        ItemStack it = eventDrop.getItemDrop().getItemStack();

        if (it.getType() == Material.NETHER_STAR && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aMenu §7(Right Click)")){
            eventDrop.setCancelled(true);

        }
    }

    // Empêche le déplacement du lit "Retour au Lobby"
    @EventHandler
    public void antiMoveReturnBedEvent(InventoryClickEvent eventAnti){

        ItemStack it = eventAnti.getCurrentItem();

        if (eventAnti.getView().getBottomInventory().contains(Material.RED_BED)){
            if (it.getType() == Material.RED_BED && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§cRetour au Lobby")){
                eventAnti.setCancelled(true);
            }
        }
    }
    // Empêche le drop du lit "Retour au Lobby"
    @EventHandler
    public void OnDropReturnBed(PlayerDropItemEvent eventDrop){

        ItemStack it = eventDrop.getItemDrop().getItemStack();

        if (it.getType() == Material.RED_BED && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§cRetour au Lobby")){
            eventDrop.setCancelled(true);

        }
    }

// Clique gauche pour intéragir avec les items dans le menu
    @EventHandler
    public void clickEvent(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();

        // Intéractions avec le menu principal "ChômageLand Menu"
        if (e.getView().getTitle().equalsIgnoreCase("§5ChômageLand Menu")){

            // Clique gauche sur le BOOK pour ouvrir le menu "Stats"
            if (e.getCurrentItem().getType().equals(Material.BOOK)){

                Inventory stats_menu = Bukkit.createInventory(player, 27, "Stats");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas encore Disponible !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] statsMenu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                stats_menu.setContents(statsMenu_items);
                player.openInventory(stats_menu);


            // Clique gauche sur le TOTEM_OF_UNDYING pour ouvrir le menu "Achievements"
            }else if (e.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)){
                Inventory achievements_menu = Bukkit.createInventory(player, 27, "Achievements");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas encore Disponible !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] achievementsMenu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                achievements_menu.setContents(achievementsMenu_items);
                player.openInventory(achievements_menu);


            // Clique gauche sur la SUNFLOWER pour ouvrir le menu "Jeux"
            }else if (e.getCurrentItem().getType().equals(Material.SUNFLOWER)) {
                Inventory play_menu = Bukkit.createInventory(player, 27, "Jeux");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack redBed = new ItemStack(Material.RED_BED);
                ItemStack grayTerra = new ItemStack(Material.GRAY_GLAZED_TERRACOTTA);
                ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta redBed_meta = redBed.getItemMeta();
                redBed_meta.setDisplayName("§aBed Wars");
                ArrayList<String> redBed_lore = new ArrayList<>();
                redBed_lore.add("§7bed wars description");
                redBed_meta.setLore(redBed_lore);
                redBed.setItemMeta(redBed_meta);

                ItemMeta grayTerra_meta = grayTerra.getItemMeta();
                grayTerra_meta.setDisplayName("§aDecked In");
                ArrayList<String> grayTerra_lore = new ArrayList<>();
                grayTerra_lore.add("§7decked in description");
                grayTerra_meta.setLore(grayTerra_lore);
                grayTerra.setItemMeta(grayTerra_meta);

                ItemMeta grassBlock_meta = grassBlock.getItemMeta();
                grassBlock_meta.setDisplayName("§aSurvival");
                ArrayList<String> grassBlock_lore = new ArrayList<>();
                grassBlock_lore.add("§7Courrez dans les champs de blé à poil");
                grassBlock_meta.setLore(grassBlock_lore);
                grassBlock.setItemMeta(grassBlock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] playMenu_items = {air, air, air, air, air, air, air, air, air, air, air, redBed, air, grayTerra, air, grassBlock, air, air, air, air, air, air, air, air, air, air, arrow};
                play_menu.setContents(playMenu_items);
                player.openInventory(play_menu);


            // Clique gauche sur le GOLD_INGOT pour ouvrir le menu "Shop"
            }else if (e.getCurrentItem().getType().equals(Material.GOLD_INGOT)){
                Inventory shop_menu = Bukkit.createInventory(player, 27, "Shop");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas encore Disponible !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] shopMenu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                shop_menu.setContents(shopMenu_items);
                player.openInventory(shop_menu);


            // Clique gauche sur le DIAMOND_BLOCK pour ouvrir le menu "Cosmétiques"
            }else if (e.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)){
                Inventory cosmetics_menu = Bukkit.createInventory(player, 27, "Cosmétiques");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas encore Disponible !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] cosmeticsMenu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                cosmetics_menu.setContents(cosmeticsMenu_items);
                player.openInventory(cosmetics_menu);

            }

        // Intéractions avec le menu "Stats"
        }else if (e.getView().getTitle().equalsIgnoreCase("Stats")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Achievements"
        }else if (e.getView().getTitle().equalsIgnoreCase("Achievements")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Jeux"
        }else if (e.getView().getTitle().equalsIgnoreCase("Jeux")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");

            // Clique gauche sur le RED_BED pour ouvrir le menu "Bed Wars"
            }else if (e.getCurrentItem().getType().equals(Material.RED_BED)){

                Inventory bedwars_menu = Bukkit.createInventory(player, 54, "Bed Wars");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack ds1v1 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds1v1v1v1 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds1v1v1v1v1v1 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds2v2 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds2v2v2v2 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds3v3 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack ds1v3 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack dsvoidless1v1v1v1 = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta ds1v1_meta = ds1v1.getItemMeta();
                ds1v1_meta.setDisplayName("§a1v1");
                ArrayList<String> ds1v1_lore = new ArrayList<>();
                ds1v1_lore.add("§71v1 bed wars description");
                ds1v1_meta.setLore(ds1v1_lore);
                ds1v1.setItemMeta(ds1v1_meta);

                ItemMeta ds1v1v1v1_meta = ds1v1v1v1.getItemMeta();
                ds1v1v1v1_meta.setDisplayName("§a1v1v1v1");
                ArrayList<String> ds1v1v1v1_lore = new ArrayList<>();
                ds1v1v1v1_lore.add("§71v1v1v1 bed wars description");
                ds1v1v1v1_meta.setLore(ds1v1v1v1_lore);
                ds1v1v1v1.setItemMeta(ds1v1v1v1_meta);

                ItemMeta ds1v1v1v1v1v1_meta = ds1v1v1v1v1v1.getItemMeta();
                ds1v1v1v1v1v1_meta.setDisplayName("§a1v1v1v1v1v1");
                ArrayList<String> ds1v1v1v1v1v1_lore = new ArrayList<>();
                ds1v1v1v1v1v1_lore.add("§71v1v1v1v1v1 bed wars description");
                ds1v1v1v1v1v1_meta.setLore(ds1v1v1v1v1v1_lore);
                ds1v1v1v1v1v1.setItemMeta(ds1v1v1v1v1v1_meta);

                ItemMeta ds2v2_meta = ds2v2.getItemMeta();
                ds2v2_meta.setDisplayName("§a2v2");
                ArrayList<String> ds2v2_lore = new ArrayList<>();
                ds2v2_lore.add("§72v2 bed wars description");
                ds2v2_meta.setLore(ds2v2_lore);
                ds2v2.setItemMeta(ds2v2_meta);

                ItemMeta ds2v2v2v2_meta = ds2v2v2v2.getItemMeta();
                ds2v2v2v2_meta.setDisplayName("§a2v2v2v2");
                ArrayList<String> ds2v2v2v2_lore = new ArrayList<>();
                ds2v2v2v2_lore.add("§72v2v2v2 bed wars description");
                ds2v2v2v2_meta.setLore(ds2v2v2v2_lore);
                ds2v2v2v2.setItemMeta(ds2v2v2v2_meta);

                ItemMeta ds3v3_meta = ds3v3.getItemMeta();
                ds3v3_meta.setDisplayName("§a3v3");
                ArrayList<String> ds3v3_lore = new ArrayList<>();
                ds3v3_lore.add("§73v3 bed wars description");
                ds3v3_meta.setLore(ds3v3_lore);
                ds3v3.setItemMeta(ds3v3_meta);

                ItemMeta ds1v3_meta = ds1v3.getItemMeta();
                ds1v3_meta.setDisplayName("§a1v3");
                ArrayList<String> ds1v3_lore = new ArrayList<>();
                ds1v3_lore.add("§71v3 bed wars description");
                ds1v3_meta.setLore(ds1v3_lore);
                ds1v3.setItemMeta(ds1v3_meta);

                ItemMeta dsvoidless1v1v1v1_meta = dsvoidless1v1v1v1.getItemMeta();
                dsvoidless1v1v1v1_meta.setDisplayName("§aVoidless 1v1v1v1");
                ArrayList<String> dsvoidless1v1v1v1_lore = new ArrayList<>();
                dsvoidless1v1v1v1_lore.add("§7Voidless 1v1v1v1 bed wars description");
                dsvoidless1v1v1v1_meta.setLore(dsvoidless1v1v1v1_lore);
                dsvoidless1v1v1v1.setItemMeta(dsvoidless1v1v1v1_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);
                //                                1    2    3    4    5    6    7    8    9    10    11    12      13      14        15        16    17    18   19   20   21   22   23   24   25   26   27   28   29   30   31   32   33   34   35   36   37      38      39    40    41    42    43         44           45   46   47   48   49   50   51   52   53   54
                ItemStack[] bedwarsMenu_items = {air, air, air, air, air, air, air, air, air, air, ds1v1, air, ds1v1v1v1, air, ds1v1v1v1v1v1, air, ds2v2, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, air, ds2v2v2v2, air, ds3v3, air, ds1v3, air, dsvoidless1v1v1v1, air, air, air, air, air, air, air, air, air, arrow};
                bedwars_menu.setContents(bedwarsMenu_items);
                player.openInventory(bedwars_menu);


            // Clique gauche sur la GRAY_GLAZED_TERRACOTTA pour ouvrir le menu "Decked Out"
            }else if (e.getCurrentItem().getType().equals(Material.GRAY_GLAZED_TERRACOTTA)){

                Inventory deckedOut_menu = Bukkit.createInventory(player, 27, "Decked Out");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas encore Disponible !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] deckedOutMenu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                deckedOut_menu.setContents(deckedOutMenu_items);
                player.openInventory(deckedOut_menu);


            // Clique gauche sur le GRASS_BLOCK pour accéder au monde survie
            }else if (e.getCurrentItem().getType().equals(Material.GRASS_BLOCK)){
                player.getInventory().clear();

                player.performCommand("joinSurvival");

            }

        // Intéractions avec le menu "Bed Wars"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars")){

            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 1v1"
            if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a1v1")){

                Inventory bw1v1_menu = Bukkit.createInventory(player, 27, "Bed Wars: 1v1");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack quartzPillar = new ItemStack(Material.QUARTZ_PILLAR);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta quartzPillar_meta = quartzPillar.getItemMeta();
                quartzPillar_meta.setDisplayName("§aHeaven");
                quartzPillar.setItemMeta(quartzPillar_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw1v1Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, quartzPillar, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw1v1_menu.setContents(bw1v1Menu_items);
                player.openInventory(bw1v1_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 1v1v1v1"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a1v1v1v1")){

                Inventory bw1v1v1v1_menu = Bukkit.createInventory(player, 27, "Bed Wars: 1v1v1v1");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw1v1v1v1Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw1v1v1v1_menu.setContents(bw1v1v1v1Menu_items);
                player.openInventory(bw1v1v1v1_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 1v1v1v1v1v1"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a1v1v1v1v1v1")) {

                Inventory bw1v1v1v1v1v1_menu = Bukkit.createInventory(player, 27, "Bed Wars: 1v1v1v1v1v1");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw1v1v1v1v1v1Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw1v1v1v1v1v1_menu.setContents(bw1v1v1v1v1v1Menu_items);
                player.openInventory(bw1v1v1v1v1v1_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 2V2"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a2v2")){

                    Inventory bw2v2_menu = Bukkit.createInventory(player, 27, "Bed Wars: 2v2");

                    ItemStack air = new ItemStack(Material.AIR);
                    ItemStack bedrock = new ItemStack(Material.BEDROCK);
                    ItemStack arrow = new ItemStack(Material.ARROW);

                    ItemMeta bedrock_meta = bedrock.getItemMeta();
                    bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                    bedrock.setItemMeta(bedrock_meta);

                    ItemMeta arrow_meta = arrow.getItemMeta();
                    arrow_meta.setDisplayName("§cRetour");
                    arrow.setItemMeta(arrow_meta);

                    ItemStack[] bw2v2Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                    bw2v2_menu.setContents(bw2v2Menu_items);
                    player.openInventory(bw2v2_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 2v2v2v2"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a2v2v2v2")){

                Inventory bw2v2v2v2_menu = Bukkit.createInventory(player, 27, "Bed Wars: 2v2v2v2");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw2v2v2v2Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw2v2v2v2_menu.setContents(bw2v2v2v2Menu_items);
                player.openInventory(bw2v2v2v2_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 3v3"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a3v3")){

                Inventory bw3v3_menu = Bukkit.createInventory(player, 27, "Bed Wars: 3v3");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw3v3Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw3v3_menu.setContents(bw3v3Menu_items);
                player.openInventory(bw3v3_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: 1v3"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§a1v3")){

                Inventory bw1v3_menu = Bukkit.createInventory(player, 27, "Bed Wars: 1v3");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bw1v3Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bw1v3_menu.setContents(bw1v3Menu_items);
                player.openInventory(bw1v3_menu);


            // Clique gauche sur la DIAMOND_SWORD pour ouvrir le menu "Bed Wars: Voidless 1v1v1v1"
            }else if (it.getType() == Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§aVoidless 1v1v1v1")){

                Inventory bwvoidless1v1v1v1_menu = Bukkit.createInventory(player, 27, "Bed Wars: Voidless 1v1v1v1");

                ItemStack air = new ItemStack(Material.AIR);
                ItemStack bedrock = new ItemStack(Material.BEDROCK);
                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemMeta bedrock_meta = bedrock.getItemMeta();
                bedrock_meta.setDisplayName("Pas de maps encore disponibles !");
                bedrock.setItemMeta(bedrock_meta);

                ItemMeta arrow_meta = arrow.getItemMeta();
                arrow_meta.setDisplayName("§cRetour");
                arrow.setItemMeta(arrow_meta);

                ItemStack[] bwvoidless1v1v1v1Menu_items = {air, air, air, air, air, air, air, air, air, air, air, air, air, bedrock, air, air, air, air, air, air, air, air, air, air, air, air, arrow};
                bwvoidless1v1v1v1_menu.setContents(bwvoidless1v1v1v1Menu_items);
                player.openInventory(bwvoidless1v1v1v1_menu);

            }else if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");

            }

        // Intéractions avec le menu "Bed Wars: 1v1"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 1v1")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.QUARTZ_PILLAR)){
                World heavenWorld = Bukkit.getWorld("Heaven");
                Location heaven = new Location(heavenWorld, 0.5, 192, 0.5, 0f, 0f);
                player.teleport(heaven);
                player.getInventory().clear();

                ItemStack lobbyReturn = new ItemStack(Material.RED_BED, 1);
                ItemMeta lobbyReturn_meta = lobbyReturn.getItemMeta();
                lobbyReturn_meta.setDisplayName("§cRetour au Lobby");
                lobbyReturn.setItemMeta(lobbyReturn_meta);
                player.getInventory().setItem(8, lobbyReturn);

                player.updateInventory();

            }

        // Intéractions avec le menu "Bed Wars: 1v1v1v1"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 1v1v1v1")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: 1v1v1v1v1v1"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 1v1v1v1v1v1")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: 2v2"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 2v2")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: 2v2v2v2"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 2v2v2v2")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: 3v3"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 3v3")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: 1v3"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: 1v3")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Bed Wars: Voidless 1v1v1v1"
        }else if (e.getView().getTitle().equalsIgnoreCase("Bed Wars: Voidless 1v1v1v1")) {
            if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                player.performCommand("menu");
            } else if (e.getCurrentItem().getType().equals(Material.BEDROCK)) {
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Decked Out"
        }else if (e.getView().getTitle().equalsIgnoreCase("Decked Out")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Shop"
        }else if (e.getView().getTitle().equalsIgnoreCase("Shop")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        // Intéractions avec le menu "Cosmétiques"
        }else if (e.getView().getTitle().equalsIgnoreCase("Cosmétiques")){
            if (e.getCurrentItem().getType().equals(Material.ARROW)){
                player.performCommand("menu");
            }else if (e.getCurrentItem().getType().equals(Material.BEDROCK)){
                e.setCancelled(true);
            }

        }

    }

}
