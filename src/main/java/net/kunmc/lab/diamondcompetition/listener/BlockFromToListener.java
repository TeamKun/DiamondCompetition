package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
    @EventHandler
    public void OnBlockFromTo(BlockFromToEvent e) {
        Material material = e.getBlock().getType();
        if (Config.preventWaterSpreading && (material.equals(Material.WATER) || material.equals(Material.STATIONARY_WATER))) {
            e.setCancelled(true);
            return;
        }

        if (Config.preventLavaSpreading && (material.equals(Material.LAVA) || material.equals(Material.STATIONARY_LAVA))) {
            e.setCancelled(true);
        }
    }
}
