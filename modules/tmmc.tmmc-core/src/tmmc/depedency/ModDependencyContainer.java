package tmmc.depedency;

import arc.struct.Seq;
import org.jetbrains.annotations.Contract;

/**
 * if you want to load more than 1 mod modules use this container
 * @author TheEE145
 */
public class ModDependencyContainer {
    public final Seq<ModDependencyModule> modules;

    @Contract(pure = true)
    public ModDependencyContainer(Seq<ModDependencyModule> modules) {
        this.modules = modules == null ? new Seq<>() : modules;
    }

    public ModDependencyContainer(ModDependencyModule... modules) {
        this(Seq.with(modules));
    }

    public ModDependencyContainer() {
        this(new Seq<>());
    }

    public Seq<ModDependencyModule> validOnly() {
        return this.modules.copy().filter(ModDependencyModule::validMod);
    }

    public void init() {
        this.modules.each(ModDependencyModule::init);
    }

    public void preload() {
        this.validOnly().each(ModDependencyModule::preload);
    }

    public void loadContent() {
        this.validOnly().each(ModDependencyModule::setAndLoadContentOnClientLoadEvent);
    }
}