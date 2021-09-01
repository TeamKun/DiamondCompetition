package net.kunmc.lab.diamondcompetition.game;

import net.kunmc.lab.diamondcompetition.Config;
import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

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

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        scoreboard.getEntries().forEach(scoreboard::resetScores);

        isStarted = true;
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

        String title;
        if (data.numberOfBlueTeamDiamonds > data.numberOfRedTeamDiamonds) {
            title = ChatColor.BLUE + "青チームの勝利";
        } else if (data.numberOfBlueTeamDiamonds < data.numberOfRedTeamDiamonds) {
            title = ChatColor.RED + "赤チームの勝利";
        } else {
            title = ChatColor.GREEN + "引き分け";
        }
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendTitle(title, "", 0, 100, 10);

            p.sendMessage(ChatColor.BLUE + "青チーム:" + data.numberOfBlueTeamDiamonds);
            p.sendMessage(ChatColor.RED + "赤チーム:" + data.numberOfRedTeamDiamonds);
        });

        return true;
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

        StartTask() {
        }

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