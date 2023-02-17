package tmmc.util;

import arc.func.Boolf;
import arc.math.geom.Vec2;
import arc.struct.Seq;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tmmc.util.math.BaseGeometry;
import tmmc.util.math.CircleHitbox;

import mindustry.gen.Building;
import java.util.Objects;

import static mindustry.Vars.world;

public class BlockUtils {
    @Contract(value = "null -> null", pure = true)
    public static @Nullable Vec2 to_vec2(Building building) {
        if(building == null || building.block == null) {
            return null;
        }

        return new Vec2(building.x, building.y);
    }

    @Contract("null, _ -> new")
    public static @NotNull Seq<Building> buildingsNearby(Building building, float range) {
        if(building == null || Float.isNaN(range)) {
            return new Seq<>();
        }

        Seq<Building> result = new Seq<>();

        int tx = building.tileX();
        int ty = building.tileY();
        int tl = (int) (range / 4);

        for(int x = tx - tl; x < tx + tl; x++) {
            if(x < 0 || x > world.width()) {
                continue;
            }

            for(int y = ty - tl; y < ty + tl; y++) {
                if(y < 0 || y > world.height()) {
                    continue;
                }

                Building build = world.build(x, y);
                if(build != null && CircleHitbox.collision(building, build, range)) {
                    result.add(build);
                }
            }
        }

        return result;
    }

    @Contract("null, _ -> fail; _, null -> fail;")
    public static byte getJoint(Building building, Boolf<Building> func) {
        Objects.requireNonNull(building);
        Objects.requireNonNull(func);

        int[] tmp = new int[2];
        BaseGeometry.eachAngle((pn) -> {
            if(func.get(world.build(pn.x, pn.y))) {
                tmp[0] += 1 << 3 - tmp[1]++;
            }
        }, BaseGeometry.of(to_vec2(building)));
        return (byte) tmp[0];
    }
}