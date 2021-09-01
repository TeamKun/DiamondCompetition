package net.kunmc.lab.diamondcompetition.command;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ArgumentType<T> {
    public static ArgumentType<Integer> INTEGER = new ArgumentType<>(Integer.class, x -> {
        Integer value = null;
        try {
            value = Integer.parseInt(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<Integer> INT = new ArgumentType<>(int.class, x -> {
        Integer value = null;
        try {
            value = Integer.parseInt(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<Double> WRAPPERDOUBLE = new ArgumentType<>(Double.class, x -> {
        Double value = null;
        try {
            value = Double.parseDouble(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<Double> DOUBLE = new ArgumentType<>(double.class, x -> {
        Double value = null;
        try {
            value = Double.parseDouble(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<Boolean> WRAPPERBOOLEAN = new ArgumentType<>(Boolean.class, x -> {
        Boolean value = null;
        try {
            value = Boolean.parseBoolean(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<Boolean> BOOLEAN = new ArgumentType<>(boolean.class, x -> {
        Boolean value = null;
        try {
            value = Boolean.parseBoolean(x);
        } catch (Exception ignore) {
        }

        return value;
    });
    public static ArgumentType<String> STRING = new ArgumentType<>(String.class, x -> {
        return x;
    });

    private final Class<T> clazz;
    private final Function<String, T> parser;

    ArgumentType(Class<T> clazz, Function<String, T> parser) {
        this.clazz = clazz;
        this.parser = parser;
    }

    public T parse(String value) {
        return parser.apply(value);
    }

    public static <T> ArgumentType<T> valueOf(Class<T> clazz) {
        for (ArgumentType argumentType : values()) {
            if (argumentType.clazz.equals(clazz)) {
                return argumentType;
            }
        }

        return null;
    }

    public static ArgumentType[] values() {
        List<ArgumentType> argumentTypeList = new ArrayList<>();
        try {
            for (Field field : ArgumentType.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    argumentTypeList.add(((ArgumentType) field.get(null)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return argumentTypeList.toArray(new ArgumentType[0]);
    }
}