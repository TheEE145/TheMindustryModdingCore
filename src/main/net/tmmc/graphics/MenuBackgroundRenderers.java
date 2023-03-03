package net.tmmc.graphics;

import arc.util.Reflect;
import mindustry.graphics.MenuRenderer;
import mindustry.ui.fragments.MenuFragment;

import static mindustry.Vars.ui;

public class MenuBackgroundRenderers {
    public static MenuRenderer getRenderer() {
        return Reflect.get(MenuFragment.class, ui.menufrag, "renderer");
    }

    public static void setRenderer(MenuRenderer renderer) {
        Reflect.set(MenuFragment.class, ui.menufrag, "renderer", renderer);
    }
}