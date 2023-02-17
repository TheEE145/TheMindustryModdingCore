package tmmc.util.math;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.struct.Seq;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.Objects;

public class BaseGeometry {
    public static final Point2 center = new Point2(0, 0);

    @Contract("null -> null; !null -> new")
    public static Point2 of(Vec2 vec2) {
        if(vec2 == null) return null;
        return new Point2(
                (int) Math.floor(vec2.x),
                (int) Math.floor(vec2.y)
        );
    }

    @Contract(value = "null -> null; !null -> new", pure = true)
    public static Vec2 of(Point2 point2) {
        if(point2 == null) return null;
        return new Vec2(point2.x, point2.y);
    }

    @Contract(" -> new")
    public static @NotNull Seq<Point2> getD4() {
        return Seq.with(Geometry.d4);
    }

    public static void eachAngle(Consumer<Point2> consumer) {
        eachAngle(consumer, center);
    }

    public static void eachAngle(Consumer<Point2> consumer, Point2 offset) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(offset);

        for(Point2 point2 : Geometry.d4) {
            consumer.accept(point2.cpy().add(offset));
        }
    }

    public static int toDirection(Point2 point2) {
        Seq<Point2> d4 = getD4();

        if(d4.contains(point2)) {
            return d4.indexOf(point2);
        }

        return 0;
    }
}