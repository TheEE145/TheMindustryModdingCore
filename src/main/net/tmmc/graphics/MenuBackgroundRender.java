package net.tmmc.graphics;

import arc.util.Reflect;

import mindustry.Vars;
import mindustry.graphics.MenuRenderer;
import mindustry.ui.fragments.MenuFragment;

public class MenuBackgroundRender extends MenuRenderer {
    private final Runnable background;

    public MenuBackgroundRender(Runnable runnable) {
        this.background = runnable;
    }

    public Runnable getBackground() {
        return this.background;
    }

    public void setAsMain() {
        Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", this);
    }

    @Override
    public void render() {
        super.render();
        this.getBackground().run();
    }

    public static void pack(Runnable runnable) {
        new MenuBackgroundRender(runnable).setAsMain();
    }
}