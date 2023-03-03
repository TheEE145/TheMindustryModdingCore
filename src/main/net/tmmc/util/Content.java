package net.tmmc.util;

import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.Scl;
import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Time;

import mindustry.ctype.UnlockableContent;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.Vars;

import arc.func.Cons;
import java.util.Objects;
import mindustry.mod.Mods;
import mindustry.ui.Styles;
import net.tmmc.graphics.FDraw;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static mindustry.Vars.iconMed;
import static mindustry.Vars.ui;

public class Content {
    public static final Seq<UnlockableContent> notShow = new Seq<>();

    static {
        notShow.add(StatusEffects.invincible);
        notShow.add(StatusEffects.none);
        notShow.add(UnitTypes.block);
    }

    @Contract("null -> fail")
    public static @NotNull String contentTypeId(UnlockableContent content) {
        return Objects.requireNonNull(content).getContentType().name().toLowerCase();
    }

    public static void each(Cons<UnlockableContent> cons) {
        Vars.content.each(cont -> {
            if(cont instanceof UnlockableContent) {
                cons.get((UnlockableContent) cont);
            }
        });
    }

    public static @NotNull Seq<UnlockableContent> get(Mods.LoadedMod mod) {
        var cont = new Seq<UnlockableContent>();

        each(content -> {
            if(content.minfo.mod == mod && !(content instanceof SpacePlanet)) {
                if(!notShow.contains(content) && !content.name.equals("ol-nothing")) {
                    cont.add(content);
                }
            }
        });

        return cont;
    }

    public static void show(UnlockableContent content) {
        try {
            ui.content.show(content);
        } catch(Throwable throwable) {
            ThrowableUtils.showDialog(throwable);
        }
    }

    public static void build(Table table, @NotNull Seq<UnlockableContent> cont) {
        int i = 0;
        int j = (int) Math.min(FDraw.width() / Scl.scl(110), 14);

        for(UnlockableContent c : cont) {
            table.button(new TextureRegionDrawable(c.uiIcon), Styles.flati, iconMed, () -> show(c))
                    .size(50f).with(im ->
            {
                var click = im.getClickListener();
                im.update(() -> {
                    var col = !click.isOver() ? Color.lightGray : Color.white;
                    im.getImage().color.lerp(col, 0.4f * Time.delta);
                });
            }).tooltip(c.localizedName);

            if(++i % j == 0) {
                table.row();
            }
        }
    }
}