package tmmc.powershell;

import arc.struct.Seq;
import org.jetbrains.annotations.NotNull;

public interface CommandArg {
    @NotNull String name();
    void parse(Seq<String> args, Command command, CommandEntry entry);
}