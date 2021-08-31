package net.kunmc.lab.diamondcompetition.game;

import org.bukkit.scheduler.BukkitRunnable;

class CountDownTask extends BukkitRunnable {
    private final Data data;
    private final Game game;

    CountDownTask(Data data, Game game) {
        this.data = data;
        this.game = game;
    }

    @Override
    public void run() {
        data.remainingTime--;

        if (data.remainingTime <= 0) {
            game.stop();
        }
    }
}
