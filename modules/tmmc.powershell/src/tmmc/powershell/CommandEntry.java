package tmmc.powershell;

import arc.struct.Seq;

import java.util.Objects;

public class CommandEntry {
    private final Seq<Command> commands = new Seq<>();
    public final Seq<String> logs = new Seq<>();
    public final Seq<Field> vars = new Seq<>();

    public String argIndex = "-";
    public String varIndex = "$";

    public Object get(String str) {
        Objects.requireNonNull(str);

        if(str.startsWith(this.varIndex)) {
            for(Field field : this.vars) {
                if(str.substring(1).equals(field.name())) {
                    return get(field.value());
                }
            }

            return null;
        }

        try {
            return Integer.parseInt(str);
        } catch(Exception ignored) {
            try {
                return Float.parseFloat(str);
            } catch(Exception ignored2) {
                return switch(str) {
                    case "null", "undefined" -> null;
                    case "true" -> true;
                    case "false" -> false;

                    default -> str;
                };
            }
        }
    }

    public void register(Command command) {
        this.commands.add(command);
    }

    public boolean unregister(Command command) {
        return this.commands.remove(command);
    }

    public void execute(String line) {
        this.execute(Objects.requireNonNull(line).split(" "));
    }

    public void log(String[] str, boolean out) {
        Objects.requireNonNull(str);

        StringBuilder st = new StringBuilder("[gray]");
        st.append(out ? ">" : "<").append("[]");

        for(String s : CommandLog.parse(str, this)) {
            st.append(" ").append(s);
        }

        this.logs.add(st.toString());
    }

    public void log(Seq<?> str, boolean out) {
        String[] str2 = new String[Objects.requireNonNull(str).size];

        for(int i = 0; i < str2.length; i++) {
            Object str3 = str.get(i);
            str2[i] = str3 == null ? "null" : str3.toString();
        }

        log(str2, out);
    }

    public void log(String str, boolean out) {
        this.log(Objects.requireNonNull(str).split(" "), out);
    }

    public void execute(String[] args) {
        Command cmd = this.get(args);
        this.log(args, false);

        if(cmd != null) {
            cmd.parse(args, this);
        } else {
            this.log("[red]command not found, please write correct command[]", true);
        }
    }

    public Command get(String[] args) {
        if(args == null || args.length == 0 || this.commands.size == 0) {
            return null;
        }

        for(Command command : commands) {
            if(args[0].equals(command.name())) {
                return command;
            }
        }

        return null;
    }
}