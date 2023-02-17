package tmmc.powershell;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

public abstract class Command {
    private final Seq<CommandArg> args = new Seq<>();
    private final String name;

    public Command register(CommandArg arg) {
        this.args.add(Objects.requireNonNull(arg));
        return this;
    }

    public void unregister(CommandArg arg) {
        this.args.remove(arg);
    }

    @Contract(pure = true)
    public Command(String name) {
        this.name = Objects.requireNonNull(name).replaceAll(" ", "_");
    }

    public String name() {
        return this.name;
    }

    public void parse(String[] args, CommandEntry entry) {
        ObjectMap<CommandArg, Seq<String>> argv = new ObjectMap<>();

        CommandArg cur = null;
        label: for(String str : Objects.requireNonNull(args)) {
            for(CommandArg arg : this.args) {
                if(arg.name().equals(str.substring(1)) && str.startsWith(entry.argIndex)) {
                    cur = arg;

                    if(!argv.containsKey(cur)) {
                        argv.put(cur, new Seq<>());
                    }

                    continue label;
                }
            }

            if(cur != null) {
                argv.get(cur).add(str);
            }
        }

        argv.forEach(elem -> {
            elem.key.parse(elem.value, this, entry);
        });
    }
}