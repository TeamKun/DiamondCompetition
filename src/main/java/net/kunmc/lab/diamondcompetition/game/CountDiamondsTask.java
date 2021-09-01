package net.kunmc.lab.diamondcompetition.game;

import net.kunmc.lab.diamondcompetition.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class CountDiamondsTask extends BukkitRunnable {
    private final Data data;

    CountDiamondsTask(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        Set<Player> blueTeamPlayers = Utils.getBlueTeam().getEntries().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        int blueTotal = 0;
        for (Player p : blueTeamPlayers) {
            PlayerInventory inv = p.getInventory();
            int numberOfDiamonds = 0;
            numberOfDiamonds += inv.all(Material.DIAMOND).values().stream()
                    .mapToInt(ItemStack::getAmount)
                    .sum();

            ItemStack offhand = inv.getItemInOffHand();
            if (offhand.getType().equals(Material.DIAMOND)) {
                numberOfDiamonds += offhand.getAmount();
            }

            Utils.getObjective().getScore(p.getName()).setScore(numberOfDiamonds);

            blueTotal += numberOfDiamonds;
        }
        data.numberOfBlueTeamDiamonds = blueTotal;

        Set<Player> redTeamPlayers = Utils.getRedTeam().getEntries().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        int redTotal = 0;
        for (Player p : redTeamPlayers) {
            PlayerInventory inv = p.getInventory();
            int numberOfDiamonds = 0;

            numberOfDiamonds += inv.all(Material.DIAMOND).values().stream()
                    .mapToInt(ItemStack::getAmount)
                    .sum();

            ItemStack offhand = inv.getItemInOffHand();
            if (offhand.getType().equals(Material.DIAMOND)) {
                numberOfDiamonds += offhand.getAmount();
            }

            Utils.getObjective().getScore(p.getName()).setScore(numberOfDiamonds);

            redTotal += numberOfDiamonds;
        }
        data.numberOfRedTeamDiamonds = redTotal;
    }
}
