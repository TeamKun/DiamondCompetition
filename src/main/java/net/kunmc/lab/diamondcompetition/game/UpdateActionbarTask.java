package net.kunmc.lab.diamondcompetition.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

class UpdateActionbarTask extends BukkitRunnable {
    private final Data data;

    UpdateActionbarTask(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendActionBar(message());
        });
    }

    private String message() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');

        builder.append(ChatColor.BLUE).append("青チーム:").append(data.numberOfBlueTeamDiamonds).append(' ');
        builder.append(ChatColor.RED).append("赤チーム:").append(data.numberOfRedTeamDiamonds).append(' ');
        builder.append(ChatColor.WHITE);
        builder.append("残り");

        int time = data.remainingTime;
        int hours = time / 3600;
        int minutes = time % 3600 / 60;
        int seconds = time % 3600 % 60;
        if (hours != 0) {
            builder.append(hours).append("時間").append(toTwoDigits(minutes)).append("分").append(toTwoDigits(seconds)).append("秒");
        } else if (minutes != 0) {
            builder.append(toTwoDigits(minutes)).append("分").append(toTwoDigits(seconds)).append("秒");
        } else {
            builder.append(toTwoDigits(seconds)).append("秒");
        }

        builder.append(')');

        return builder.toString();
    }

    private String toTwoDigits(int n) {
        return String.format("%02d", n);
    }
}
