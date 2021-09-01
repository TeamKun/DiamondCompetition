package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.Config;
import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import net.kunmc.lab.diamondcompetition.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class ConfigShowCommand implements SubCommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        try {
            for (Field field : Config.class.getDeclaredFields()) {
                sender.sendMessage(ChatColor.GREEN + field.getName() + ": " + field.get(null));
            }

            Field dataField = Game.class.getDeclaredField("data");
            dataField.setAccessible(true);
            Object data = dataField.get(DiamondCompetition.game);

            Field timeField = data.getClass().getDeclaredField("remainingTime");
            timeField.setAccessible(true);
            Object remainingTime = timeField.get(data);
            
            sender.sendMessage(ChatColor.GREEN + "remainingTime: " + remainingTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
