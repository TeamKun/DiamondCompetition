package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShufflePlayersCommand implements SubCommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        Team blueTeam = Utils.getBlueTeam();
        Team redTeam = Utils.getRedTeam();
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        playerList = playerList.stream()
                .filter(p -> !blueTeam.hasEntry(p.getName()))
                .filter(p -> !redTeam.hasEntry(p.getName()))
                .collect(Collectors.toList());
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

        sender.sendMessage(ChatColor.GREEN + "" + count + "人のプレイヤーをランダムにチームに割り当てました.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
