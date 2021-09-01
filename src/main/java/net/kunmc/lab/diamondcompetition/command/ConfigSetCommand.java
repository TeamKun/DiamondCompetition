package net.kunmc.lab.diamondcompetition.command;

import net.kunmc.lab.diamondcompetition.Config;
import net.kunmc.lab.diamondcompetition.DiamondCompetition;
import net.kunmc.lab.diamondcompetition.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConfigSetCommand implements SubCommand {
    private final List<ConfigItem> configItemList = new ArrayList<>();

    public ConfigSetCommand() {
        try {
            for (Field field : Config.class.getDeclaredFields()) {
                if (field.getName().equals("updateInterval")) {
                    configItemList.add(new ConfigItem(field, null, int.class, x -> {
                        DiamondCompetition.game.changeUpdateInterval(x);
                    }));
                    continue;
                }
                configItemList.add(new ConfigItem(field, null));
            }

            Field dataField = Game.class.getDeclaredField("data");
            dataField.setAccessible(true);
            Object data = dataField.get(DiamondCompetition.game);
            configItemList.add(new ConfigItem(data.getClass().getDeclaredField("remainingTime"), data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "usage: /diamondcompetition config set <configItem> <value>");
            return;
        }

        String itemName = args[0];
        if (!isCollectItemName(itemName)) {
            sender.sendMessage(ChatColor.RED + itemName + "は不正なコンフィグ名です.");
            return;
        }
        ConfigItem configItem = getConfigItemFromName(itemName);

        Object value = ArgumentType.valueOf(configItem.clazz).parse(args[1]);
        if (value == null) {
            sender.sendMessage(ChatColor.RED + args[1] + "は不正な値です.");
            return;
        }

        configItem.setValue(value);
        sender.sendMessage(ChatColor.GREEN + configItem.name() + "の値を" + value + "に設定しました.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completion = new ArrayList<>();

        if (args.length == 1) {
            completion.addAll(configItemList.stream().map(ConfigItem::name).collect(Collectors.toList()));
        }

        if (args.length == 2 && isCollectItemName(args[0])) {
            completion.add("<" + getConfigItemFromName(args[0]).simpleClassName() + ">");
        }

        return completion;
    }

    private boolean isCollectItemName(String itemName) {
        return configItemList.stream().anyMatch(x -> x.name().equals(itemName));
    }

    private ConfigItem getConfigItemFromName(String itemName) {
        return configItemList.stream().filter(x -> x.name().equals(itemName)).findFirst().orElse(null);
    }
}

class ConfigItem {
    Class clazz;
    Field field;
    Object target;
    Consumer consumer;

    public ConfigItem(Field field, Object target) {
        this.clazz = field.getType();
        this.field = field;
        this.target = target;
        this.consumer = null;
    }

    public <T> ConfigItem(Field field, Object target, Class<T> clazz, Consumer<T> consumer) {
        this(field, target);
        this.consumer = consumer;
    }

    public String simpleClassName() {
        return clazz.getSimpleName();
    }

    public String name() {
        return field.getName();
    }

    public Object getValue() {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setValue(Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (consumer != null) {
            consumer.accept(value);
        }
    }
}