package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
    @EventHandler
    public void OnBlockFromTo(BlockFromToEvent e) {
        if (Config.preventLiquidSpreading) {
            e.setCancelled(true);
        }
    }
}
