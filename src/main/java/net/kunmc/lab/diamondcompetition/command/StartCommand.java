package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import net.kunmc.lab.diamondcompetition.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class StartCommand implements SubCommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        Game game = DiamondCompetition.game;
        if (game.isStarted()) {
            sender.sendMessage(ChatColor.RED + "ゲームは既に開始されています.");
            return;
        }

        game.start();
        sender.sendMessage(ChatColor.GREEN + "ゲームを開始します.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
