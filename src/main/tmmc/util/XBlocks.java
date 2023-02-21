package tmmc.util;

import arc.func.Boolf;
import arc.math.geom.Vec2;

import mindustry.world.Tile;
import mindustry.world.Block;
import mindustry.gen.Building;

import org.jetbrains.annotations.Contract;

public class XBlocks {
    @Contract(value = "null -> null", pure = true)
    public static Building of(Tile tile) {
        if(tile == null) return null;
        return tile.build;
    }

    @Contract(value = "null -> null", pure = true)
    public static Block of(Building building) {
        if(building == null) return null;
        return building.block;
    }

    @Contract(value = "null -> null", pure = true)
    public static Vec2 to_vec2(Building building) {
        if(building == null) return null;
        return new Vec2(building.x, building.y);
    }

    public static byte jointsBlock(Building building) {
        Block motherBlock = of(building);
        if(motherBlock == null) return (byte) -1;
        return jointsBlock(building, motherBlock::equals);
    }

    public static byte jointsBlock(Building building, Boolf<Block> boolf) {
        return jointsBuild(building, build -> boolf.get(XBlocks.of(build)));
    }

    public static byte jointsBuild(Building building, Boolf<Building> boolf) {
        return joints(building, tile -> boolf.get(XBlocks.of(tile)));
    }

    public static byte joints(Building building, Boolf<Tile> boolf) {
        if(building == null) return (byte) -1;
        int[] buffer = new int[] {0, 3};
        Geom.each4dAngle(point -> {
            if(boolf != null && boolf.get(XWorld.at(point))) {
                buffer[0] += 1 << buffer[1]--;
            }
        }, Geom.toPoint(building));
        return (byte) buffer[0];
    }
}