package net.tmmc.util;

import arc.math.geom.Vec2;
import mindustry.gen.Building;
import mindustry.world.Tile;
import org.jetbrains.annotations.Contract;

import static mindustry.Vars.world;

public class DefMath {
    public static final float theta = (float) (Math.PI * 2);

    @Contract(pure = true)
    public static float thx(float angle, float rad) {
        return (float) (Math.cos((angle / 360) * theta) * rad);
    }

    @Contract(pure = true)
    public static float thy(float angle, float rad) {
        return (float) (Math.sin((angle / 360) * theta) * rad);
    }

    public static float len(float x1, float x2, float y1, float y2) {
        return (float) Math.sqrt(pow(x2 - x1) + pow(y2 - y1));
    }

    public static Tile getTileByDraw(float x, float y) {
        return world.tile((int) Math.floor(x / 8), (int) Math.floor(y / 8));
    }

    public static boolean collision(float x1, float y1, float x2, float y2, float radius) {
        return len(x1, x2, y1, y2) < radius;
    }

    @Contract("null, _, _ -> false; !null, null, _ -> false")
    public static boolean collision(Building ab1, Building bb2, float radius) {
        if(ab1 == null || bb2 == null || Float.isNaN(radius)) {
            return false;
        }

        return collision(XBlocks.to_vec2(ab1), XBlocks.to_vec2(bb2), radius);
    }

    @Contract("null, _, _ -> false; !null, null, _ -> false")
    public static boolean collision(Vec2 ab1, Vec2 bb2, float radius) {
        if(ab1 == null || bb2 == null || Float.isNaN(radius)) {
            return false;
        }

        return collision(ab1.x, ab1.y, bb2.x, bb2.y, radius);
    }

    @Contract(pure = true)
    public static float pow(float n) {
        return n * n;
    }
}