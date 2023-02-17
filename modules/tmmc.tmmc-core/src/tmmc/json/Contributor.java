package tmmc.json;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Contributor(String name, boolean freesound) {
    public static final String freesoundPrefix = " (freesound.org)";

    @Contract(pure = true)
    public @NotNull String fullName() {
        return this.name + (this.freesound ? freesoundPrefix : "");
    }
}