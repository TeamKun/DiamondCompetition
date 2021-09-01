package net.kunmc.lab.diamondcompetition.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigCommand implements SubCommand {
    private final Map<String, SubCommand> subCmdMap = new HashMap<String, SubCommand>() {{
        put("set", new ConfigSetCommand());
        put("show", new ConfigShowCommand());
    }};

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/diamondcompetition config set <configItem> <value>");
            sender.sendMessage(ChatColor.RED + "/diamondcompetition config show");
            return;
        }

        if (subCmdMap.containsKey(args[0])) {
            subCmdMap.get(args[0]).run(sender, MainCommand.nextArgs(args));
        } else {
            sender.sendMessage(ChatColor.RED + "不明なコマンドです.");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completion = new ArrayList<>();

        if (args.length == 1) {
            completion.addAll(subCmdMap.keySet());
        }

        if (args.length > 1 && subCmdMap.containsKey(args[0])) {
            completion.addAll(subCmdMap.get(args[0]).tabComplete(sender, MainCommand.nextArgs(args)));
        }

        return completion;
    }
}
