package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShufflePlayersCommand implements SubCommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(playerList);

        int count = 0;
        for (Player p : playerList) {
            if (count % 2 == 0) {
                Utils.getBlueTeam().addEntry(p.getName());
            } else {
                Utils.getRedTeam().addEntry(p.getName());
            }
            count++;
        }

        sender.sendMessage(ChatColor.GREEN + "現在オンラインのプレイヤーの所属チームをシャッフルしました.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
