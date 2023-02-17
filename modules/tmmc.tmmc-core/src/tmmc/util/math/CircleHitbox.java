package tmmc.util.math;

import org.jetbrains.annotations.Contract;
import mindustry.gen.Building;
import tmmc.util.BlockUtils;
import arc.math.geom.Vec2;

public class CircleHitbox {
    public static boolean collision(float x1, float y1, float x2, float y2, float radius) {
        return Lengths.len(x1, x2, y1, y2) < radius;
    }

    @Contract("null, _, _ -> false; !null, null, _ -> false")
    public static boolean collision(Vec2 vec1, Vec2 vec2, float radius) {
        if(vec1 == null || vec2 == null) {
            return false;
        }

        return collision(vec1.x, vec1.y, vec2.x, vec2.y, radius);
    }

    public static boolean collision(Building ab, Building bb, float radius) {
        return collision(BlockUtils.to_vec2(ab), BlockUtils.to_vec2(bb), radius);
    }
}