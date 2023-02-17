package tmmc.depedency;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.mod.Mods;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.Block;
import org.jetbrains.annotations.Contract;
import tmmc.util.ModUtils;

import static mindustry.Vars.content;

/**
 * Used to modify a mod or dependency mod using this module class
 * if a need mod enabled when module can add item to this mod or modify item from this mod
 *
 * @author TheEE145
 */
public abstract class ModDependencyModule {
    public Seq<UnlockableContent> transformations = new Seq<>();
    public Mods.LoadedMod loadedMod;

    @Contract(pure = true)
    public ModDependencyModule(Mods.LoadedMod loadedMod) {
        this.loadedMod = loadedMod;
    }

    @Contract(pure = true)
    public ModDependencyModule() {
        this(null);
    }

    public void init() {
    }

    public boolean validMod() {
        return ModUtils.isValidMod(this.loadedMod);
    }

    public String prefix(String name) {
        if(!this.validMod()) {
            return name;
        }

        String pref = this.loadedMod.name + "-";
        if(name.startsWith(pref)) {
            return name;
        }

        return pref + name;
    }

    public Item itemFromMod(String name) {
        return content.item(this.prefix(name));
    }

    public Liquid liquidFromMod(String name) {
        return content.liquid(this.prefix(name));
    }

    public Block blockFromMod(String name) {
        return content.block(this.prefix(name));
    }

    public UnitType unitTypeFromMod(String name) {
        return content.unit(this.prefix(name));
    }

    public StatusEffect effectFromMod(String name) {
        return content.statusEffect(this.prefix(name));
    }

    public void preload() {
    }

    public void setAndLoadContentOnClientLoadEvent() {
        this.setAndLoadContent(true);
    }

    public void setAndLoadContent(boolean clientLoadEvent) {
        Mods.LoadedMod current = ModUtils.getCurrentMod();
        content.setCurrentMod(this.loadedMod);
        this.loadContent();

        if(clientLoadEvent) {
            AssetsTransformProcessor.transform();

            this.transformations.each(transformElement -> {
                transformElement.init();
                transformElement.load();
                transformElement.loadIcon();
            });
        }

        content.setCurrentMod(current);
    }

    public abstract void loadContent();
}