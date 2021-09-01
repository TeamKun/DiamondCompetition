package net.kunmc.lab.diamondcompetition.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;
import java.util.stream.Collectors;

public class MainCommand implements TabExecutor {
    private final Map<String, SubCommand> subCmdMap = new HashMap<String, SubCommand>() {{
        put("start", new StartCommand());
        put("stop", new StopCommand());
        put("config", new ConfigCommand());
        put("shuffleplayers", new ShufflePlayersCommand());
    }};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (subCmdMap.containsKey(args[0])) {
            subCmdMap.get(args[0]).run(sender, nextArgs(args));
        } else {
            sender.sendMessage(ChatColor.RED + "不明なコマンドです.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completion = new ArrayList<>();

        if (args.length == 1) {
            completion.addAll(subCmdMap.keySet());
        }

        if (args.length > 1 && subCmdMap.containsKey(args[0])) {
            completion.addAll(subCmdMap.get(args[0]).tabComplete(sender, nextArgs(args)));
        }

        return completion.stream().filter(x -> x.startsWith(args[args.length - 1])).collect(Collectors.toList());
    }

    public static String[] nextArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}
