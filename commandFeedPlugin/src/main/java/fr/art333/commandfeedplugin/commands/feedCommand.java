package fr.art333.commandfeedplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class feedCommand implements CommandExecutor {

    // Mise en place de l'algorithme de la commande "/feed" permettant de se nourrir au maximum dans le jeu
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.setFoodLevel(20);
            player.sendMessage("§6'akalat jayidaan ! (J'ai bien mangé wola)");

        }
        return true;
    }
}