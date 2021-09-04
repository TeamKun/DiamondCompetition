package net.kunmc.lab.diamondcompetition;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Utils {
    public static Set<Team> getTeams() {
        return getServer().getScoreboardManager().getMainScoreboard().getTeams();
    }

    public static Objective getObjective() {
        Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        Objective objective = scoreboard.getObjective(Const.objectiveName);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(Const.objectiveName, "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        return objective;
    }
}
