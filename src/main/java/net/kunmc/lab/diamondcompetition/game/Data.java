package net.kunmc.lab.diamondcompetition.game;

import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

class Data {
    Map<Team, Integer> teamToScoreMap = new HashMap<>();
    int remainingTime = 0;
}
