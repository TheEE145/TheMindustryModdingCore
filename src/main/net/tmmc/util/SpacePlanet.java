package net.tmmc.util;

import arc.struct.Seq;
import mindustry.type.Planet;

import static mindustry.Vars.content;

public class SpacePlanet extends Planet {
    public static final Seq<SpacePlanet> spaces = new Seq<>();

    public static void append(Planet parent, int count) {
        var current = ModUtils.getCurrentMod();
        content.setCurrentMod(null);
        for(int i = 0; i < count; i++) {
            new SpacePlanet(parent);
        }

        content.setCurrentMod(current);
    }

    public SpacePlanet(Planet parent) {
        super(Math.random() + "", parent, 0);
        this.accessible = false;
        spaces.add(this);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean isVanilla() {
        return false;
    }

    @Override
    public boolean visible() {
        return false;
    }

    @Override
    public boolean unlockedNowHost() {
        return false;
    }

    @Override
    public boolean unlocked() {
        return false;
    }

    @Override
    public boolean unlockedNow() {
        return false;
    }
}