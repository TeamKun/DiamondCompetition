package net.kunmc.lab.diamondcompetition.listener;

import net.kunmc.lab.diamondcompetition.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockPlaceListener implements Listener {
    private final Set<Material> redstoneMaterialSet = new HashSet<Material>() {{
        add(Material.DISPENSER);
        add(Material.PISTON_BASE);
        add(Material.PISTON_STICKY_BASE);
        add(Material.LEVER);
        add(Material.STONE_PLATE);
        add(Material.WOOD_PLATE);
        add(Material.REDSTONE_TORCH_ON);
        add(Material.REDSTONE_TORCH_OFF);
        add(Material.TRIPWIRE_HOOK);
        add(Material.TRAPPED_CHEST);
        add(Material.GOLD_PLATE);
        add(Material.IRON_PLATE);
        add(Material.DAYLIGHT_DETECTOR);
        add(Material.DAYLIGHT_DETECTOR_INVERTED);
        add(Material.REDSTONE_BLOCK);
        add(Material.DROPPER);
        add(Material.OBSERVER);
        add(Material.DIODE);
        add(Material.DIODE_BLOCK_ON);
        add(Material.DIODE_BLOCK_OFF);
        add(Material.REDSTONE_COMPARATOR);
        add(Material.REDSTONE_COMPARATOR_ON);
        add(Material.REDSTONE_COMPARATOR_OFF);
    }};

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (Config.preventPlacingSomeRedstones && redstoneMaterialSet.contains(e.getBlockPlaced().getType())) {
            e.setCancelled(true);
        }
    }
}
