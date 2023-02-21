package tmmc.json;

import arc.files.Fi;
import arc.func.Cons;

import mindustry.mod.Mod;
import mindustry.mod.Mods.ModMeta;
import mindustry.mod.Mods.LoadedMod;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tmmc.annotations.inteji.OverrideField;
import tmmc.annotations.inteji.XJsonField;

import java.util.Arrays;
import java.util.Objects;

public class JsonMod extends LoadedMod {
    public static final String jitpackDomain = "https://www.jitpack.io/#";
    public static final String githubDomain = "https://github.com/";

    public static @NotNull JsonMod create(Mod mod, LoadedMod raw) {
        Objects.requireNonNull(raw);
        String json = raw.root.child("mod.json").readString("UTF-8");
        if(json == null) return new JsonMod(raw);

        Objects.requireNonNull(mod);
        JsonMod mod1 = XJsonIO.read(json, JsonMod.class);
        mod1.raw = raw;
        mod1.main = mod;
        mod1.file = raw.file;
        mod1.root = raw.root;
        mod1.loader = raw.loader;
        return mod1;
    }

    @XJsonField private String name;
    @XJsonField private String author;
    @XJsonField private String displayName;
    @XJsonField private String description;
    @XJsonField private String version;
    @XJsonField private String minGameVersion;
    @XJsonField private boolean java;
    @XJsonField private Contributors contributors;
    @XJsonField private Repository repository;

    @OverrideField private transient Mod main;
    @OverrideField private transient Fi file, root;
    @OverrideField private transient ClassLoader loader;
    @OverrideField private transient ModMeta meta;

    private transient LoadedMod raw;

    public JsonMod(Fi file, Fi root, Mod main, ClassLoader loader, ModMeta meta, LoadedMod raw)  {
        super(file, root, main, loader, meta);
        this.raw = raw;
    }

    public JsonMod(@NotNull LoadedMod mod) {
        this(mod.file, mod.root, mod.main, mod.loader, mod.meta, mod);
    }

    public void applySelf() {
        this.apply(this.meta);
    }

    public void apply(ModMeta meta) {
        if(meta != null) {
            this.name = meta.name;
            this.author = meta.author;
            this.displayName = meta.displayName;
            this.description = meta.description;
            this.version = meta.description;
            this.minGameVersion = meta.description;
            this.java = meta.java;
            this.meta = meta;
        }
    }

    /**
     * Used to work with ModMeta linked method.
     * if you use this to get meta into class field when use {@link JsonMod#applySelf()} after every change
     * @param cons handler
     */
    public void getMeta(Cons<ModMeta> cons) {
        if (cons != null) {
            cons.get(this.meta);
            this.applySelf();
        }
    }

    public boolean validContributors() {
        return this.contributors != null && this.contributors.valid();
    }

    public String getRepoURL() {
        if(this.repository != null) {
            return this.repository.asUrl();
        } else {
            return null;
        }
    }

    public String getAuthors() {
        if(this.validContributors()) {
            return this.contributors.create(this).toString();
        } else {
            return this.author;
        }
    }

    public record Contributor(String name, boolean freesound) {
        @Contract(pure = true)
        public @NotNull String fullName() {
            return this.name + (this.freesound ? " (freesound.org)" : "");
        }
    }

    /**
     * Used to store list of contributors in mod json data. can include itself
     * @param list list of contributors, if you have in this list set append to true
     * @param append set false if you have in list yourself
     */
    public record Contributors(Contributor[] list, boolean append) {
        @Contract(pure = true)
        public boolean valid() {
            return this.list != null && this.list.length > 0;
        }

        public @NotNull StringBuilder create(String author) {
            var builder = new StringBuilder();

            if(!this.valid()) {
                return builder;
            }

            if(this.append) {
                builder.append(author);
            }

            boolean[] first = {!this.append};
            Arrays.stream(list).forEach(contributor -> {
                if(!first[0]) {
                    builder.append("\n");
                } else {
                    first[0] = false;
                }

                builder.append(contributor.fullName());
            });

            return builder;
        }

        public @NotNull StringBuilder create(@NotNull JsonMod mod) {
            return this.create(mod.author);
        }
    }

    public record Repository(String user, String name) {
        @Contract(pure = true)
        public @NotNull String asUrl() {
            return githubDomain + user + "/" + name;
        }

        @Contract(pure = true)
        public @NotNull String asJitpackUrl() {
            return jitpackDomain + user + "/" + name;
        }
    }

    //getters

    @Override
    public boolean isJava() {
        return this.java;
    }

    public Contributors getContributors() {
        return this.contributors;
    }

    public LoadedMod getRaw() {
        return this.raw;
    }

    public ModMeta getMeta() {
        return this.meta;
    }

    public Repository getRepository() {
        return this.repository;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Mod getMain() {
        return this.main;
    }

    public String getMinGameVersion() {
        return this.minGameVersion;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }
}