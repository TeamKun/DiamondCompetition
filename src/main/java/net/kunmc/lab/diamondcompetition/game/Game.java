package net.kunmc.lab.diamondcompetition.game;

import net.kunmc.lab.diamondcompetition.Config;
import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private boolean isStarted = false;
    private final Data data;
    private final List<BukkitTask> bukkitTaskList = new ArrayList<>();

    public Game() {
        this.data = new Data();
    }

    public boolean start() {
        if (isStarted) {
            return false;
        }
        isStarted = true;

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        scoreboard.getEntries().forEach(scoreboard::resetScores);
        data.teamToScoreMap.clear();

        bukkitTaskList.add(new StartTask().runTaskTimer(DiamondCompetition.instance, 0, 20));
        data.remainingTime = Config.matchTime;

        return true;
    }

    public boolean stop() {
        if (!isStarted) {
            return false;
        }
        isStarted = false;

        bukkitTaskList.forEach(BukkitTask::cancel);
        bukkitTaskList.clear();

        //終了時に集計処理を行う
        new CountDiamondsTask(data).run();

        List<Team> teamList = rankedTeamList();
        if (teamList.isEmpty()) {
            return true;
        }
        
        Team firstTeam = teamList.get(0);
        String title = firstTeam.getColor() + firstTeam.getName() + "チームの勝利";

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendTitle(title, "", 0, 100, 10);

            for (int i = 0; i < teamList.size(); i++) {
                Team team = teamList.get(i);
                p.sendMessage(String.format("%s%d位 %s %d個", team.getColor().toString(), i + 1, team.getName(), data.teamToScoreMap.get(team)));
            }
        });

        return true;
    }

    private List<Team> rankedTeamList() {
        List<Team> list = Arrays.stream(data.teamToScoreMap.keySet().toArray(new Team[0]))
                .sorted(Comparator.comparingInt(x -> data.teamToScoreMap.get(x)))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    public boolean changeUpdateInterval(int updateInterval) {
        if (!isStarted) {
            return false;
        }

        bukkitTaskList.forEach(BukkitTask::cancel);
        bukkitTaskList.clear();

        startGameTasks(updateInterval);

        return true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    private void startGameTasks(int updateInterval) {
        JavaPlugin plugin = DiamondCompetition.instance;
        bukkitTaskList.add(new CountDiamondsTask(data).runTaskTimerAsynchronously(plugin, 0, updateInterval));
        bukkitTaskList.add(new CountDownTask(data, this).runTaskTimerAsynchronously(plugin, 0, 20));
        bukkitTaskList.add(new UpdateActionbarTask(data).runTaskTimerAsynchronously(plugin, 0, updateInterval));
    }

    private class StartTask extends BukkitRunnable {
        private int time = 5;

        @Override
        public void run() {
            if (time > 0) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendActionBar(String.format("%d秒後にゲームを開始します", time));
                });
                time--;
            } else {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendTitle("", "試合開始", 0, 60, 10);
                });

                startGameTasks(Config.updateInterval);

                this.cancel();
            }
        }
    }
}