package tmmc.json;

import arc.Core;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Repository(String name, String user) {
    @Contract(pure = true)
    public @NotNull String asURL() {
        return "https://github.com/" + this.user + "/" + this.name;
    }

    @Contract(pure = true)
    public @NotNull String asImplementation() {
        return "com.github." + this.user + "." + this.name;
    }

    @Contract(pure = true)
    public @NotNull String asImplementation(String moduleName, String version) {
        return this.asImplementation() + ":" + moduleName + ":" + version;
    }

    public void open() {
        Core.app.openURI(this.asURL());
    }
}
