package net.kunmc.lab.diamondcompetition.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    void run(CommandSender sender, String[] args);

    List<String> tabComplete(CommandSender sender, String[] args);
}
