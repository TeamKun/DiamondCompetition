package net.kunmc.lab.diamondcompetition;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static org.bukkit.Bukkit.getServer;

public class Utils {
    public static Team getBlueTeam() {
        Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        Team blueTeam = scoreboard.getTeam(Const.blueTeamName);
        if (blueTeam == null) {
            blueTeam = scoreboard.registerNewTeam(Const.blueTeamName);
        }
        blueTeam.setColor(ChatColor.BLUE);
        blueTeam.setPrefix(ChatColor.BLUE.toString());

        return blueTeam;
    }

    public static Team getRedTeam() {
        Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        Team redTeam = scoreboard.getTeam(Const.redTeamName);
        if (redTeam == null) {
            redTeam = scoreboard.registerNewTeam(Const.redTeamName);
        }
        redTeam.setColor(ChatColor.RED);
        redTeam.setPrefix(ChatColor.RED.toString());

        return redTeam;
    }
}
