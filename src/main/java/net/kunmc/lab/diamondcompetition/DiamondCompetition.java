package net.kunmc.lab.diamondcompetition;

import net.kunmc.lab.diamondcompetition.command.MainCommand;
import net.kunmc.lab.diamondcompetition.game.Game;
import net.kunmc.lab.diamondcompetition.listener.BlockFromToListener;
import net.kunmc.lab.diamondcompetition.listener.PortalCreateListener;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiamondCompetition extends JavaPlugin {
    public static DiamondCompetition instance;
    public static Game game;

    @Override
    public void onEnable() {
        instance = this;
        game = new Game();

        Utils.getBlueTeam();
        Utils.getRedTeam();

        TabExecutor command = new MainCommand();
        getServer().getPluginCommand("diamondcompetition").setExecutor(command);
        getServer().getPluginCommand("diamondcompetition").setTabCompleter(command);

        getServer().getPluginManager().registerEvents(new BlockFromToListener(), this);
        getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
