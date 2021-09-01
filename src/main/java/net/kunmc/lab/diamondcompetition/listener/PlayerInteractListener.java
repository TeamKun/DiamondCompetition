package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerInteractListener implements Listener {
    private final Set<Material> vehicleSet = new HashSet<Material>() {{
        add(Material.MINECART);
        add(Material.STORAGE_MINECART);
        add(Material.POWERED_MINECART);
        add(Material.EXPLOSIVE_MINECART);
        add(Material.HOPPER_MINECART);
        add(Material.BOAT);
        add(Material.BOAT_JUNGLE);
        add(Material.BOAT_SPRUCE);
        add(Material.BOAT_ACACIA);
        add(Material.BOAT_BIRCH);
        add(Material.BOAT_DARK_OAK);
    }};

    @EventHandler

    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        Material material = e.getMaterial();
        if (Config.preventPlacingSomeRedstones && material.equals(Material.REDSTONE)) {
            e.setCancelled(true);
            return;
        }

        if (Config.preventIgnition && material.equals(Material.FLINT_AND_STEEL)) {
            e.setCancelled(true);
            return;
        }

        if (Config.preventPlacingVehicles && vehicleSet.contains(material)) {
            e.setCancelled(true);
        }
    }
}
