package tmmc.util;

import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import arc.util.ArcRuntimeException;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static mindustry.Vars.*;

public class XWorld {
    @Contract(pure = true)
    public static void checkWorld() {
        if(world == null || world.tiles == null) {
            throw new ArcRuntimeException("world not loaded");
        }
    }

    public static Block blockAt(int x, int y) {
        return XBlocks.of(buildingAt(x, y));
    }

    public static Block blockAt(Point2 point2) {
        return XBlocks.of(buildingAt(point2));
    }

    public static Block blockAt(int pos) {
        return XBlocks.of(buildingAt(pos));
    }

    public static Building buildingAt(int x, int y) {
        return XBlocks.of(at(x, y));
    }

    public static Building buildingAt(Point2 point2) {
        return XBlocks.of(at(point2));
    }

    public static Building buildingAt(int pos) {
        return XBlocks.of(at(pos));
    }

    public static Tile at(int x, int y) {
        return world.tile(x, y);
    }

    @Contract("null -> null")
    public static Tile at(Point2 point2) {
        if(point2 == null) return null;
        return at(point2.x, point2.y);
    }

    public static Tile at(int pos) {
        return world.tile(pos);
    }
}