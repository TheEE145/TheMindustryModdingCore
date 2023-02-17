package tmmc.powershell;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandLog {
    public static String @NotNull[] parse(String[] args, CommandEntry entry) {
        Objects.requireNonNull(entry);
        Objects.requireNonNull(args);

        if(args.length == 0) {
            return args;
        }

        String[] str = new String[args.length];
        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            str[i] = parse(arg, entry);
        }

        return str;
    }

    public static String parse(String string, CommandEntry entry) {
        Objects.requireNonNull(string);
        Objects.requireNonNull(entry);

        return switch(string) {
            case "true", "false" -> "[blue]" + string + "[]";
            case "undefined", "null" -> "[darkgray]" + string + "[]";

            default -> {
                if(string.startsWith(entry.argIndex)) {
                    yield "[purple]" + string + "[]";
                }

                if(string.startsWith("$")) {
                    yield "[green]" + string + "[]";
                }

                try {
                    yield "[blue]" + Integer.parseInt(string) + "[]";
                } catch(Throwable ignored) {
                    try {
                        yield "[blue]" + Float.parseFloat(string) + "[]";
                    } catch(Throwable ignored2) {
                        yield string;
                    }
                }
            }
        };
    };
}