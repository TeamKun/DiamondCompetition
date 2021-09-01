package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        if (Config.preventNetherPortal) {
            e.setCancelled(true);
        }
    }
}
