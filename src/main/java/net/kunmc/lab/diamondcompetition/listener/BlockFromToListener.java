package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
    @EventHandler
    public void OnBlockFromTo(BlockFromToEvent e) {
        if (Config.preventWaterSpreading && e.getBlock().getType().equals(Material.WATER)) {
            e.setCancelled(true);
            return;
        }

        if (Config.preventLavaSpreading && e.getBlock().getType().equals(Material.LAVA)) {
            e.setCancelled(true);
        }
    }
}
