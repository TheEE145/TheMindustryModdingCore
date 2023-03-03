package net.tmmc.graphics;

import mindustry.graphics.MenuRenderer;

public class MenuBackgroundRender extends MenuRenderer {
    private final Runnable background;

    public MenuBackgroundRender(Runnable runnable) {
        this.background = runnable;
    }

    public Runnable getBackground() {
        return this.background;
    }

    public void setAsMain() {
        MenuBackgroundRenderers.setRenderer(this);
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