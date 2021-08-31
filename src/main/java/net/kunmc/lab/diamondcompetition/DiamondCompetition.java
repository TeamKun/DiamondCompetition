package net.kunmc.lab.diamondcompetition;

import org.bukkit.plugin.java.JavaPlugin;

public final class DiamondCompetition extends JavaPlugin {
    public static DiamondCompetition instance;

    @Override
    public void onEnable() {
        instance = this;

        Utils.getBlueTeam();
        Utils.getRedTeam();
    }

    @Override
    public void onDisable() {
    }
}
