package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import net.kunmc.lab.diamondcompetition.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class StopCommand implements SubCommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        Game game = DiamondCompetition.game;
        if (!game.isStarted()) {
            sender.sendMessage(ChatColor.RED + "ゲームは開始されていません.");
            return;
        }

        game.stop();
        sender.sendMessage(ChatColor.GREEN + "ゲームを終了します.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
