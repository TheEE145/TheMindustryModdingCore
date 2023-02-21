package net.tmmc.util;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.func.Cons;

import mindustry.gen.Building;
import org.jetbrains.annotations.Contract;

public class Geom {
    public static final Point2 center = new Point2(0, 0);

    public static final Seq<Point2> d8edge = Seq.with(Geometry.d8edge);
    public static final Seq<Point2> d4c = Seq.with(Geometry.d4c);
    public static final Seq<Point2> d8 = Seq.with(Geometry.d8);
    public static final Seq<Point2> d4 = Seq.with(Geometry.d4);

    @Contract(value = "null -> null; !null -> new", pure = true)
    public static Point2 toPoint(Vec2 vec2) {
        if(vec2 == null) return null;
        return new Point2((int) vec2.x, (int) vec2.y);
    }

    @Contract("null -> null; !null -> new")
    public static Point2 toPoint(Building building) {
        if(building == null) return null;
        return new Point2(building.tileX(), building.tileY());
    }

    public static Point2 toPoint(int rotation) {
        try {
            return d4.get(rotation);
        } catch(Throwable ignored) {
            return center;
        }
    }

    public static int toRotation(Point2 point2) {
        if(d4.contains(point2)) {
            return d4.indexOf(point2);
        } else return 0;
    }

    public static void each4dAngle(Cons<Point2> cons) {
        each4dAngle(cons, center);
    }

    public static void each4dAngle(Cons<Point2> cons, Point2 offset) {
        if(cons != null) {
            for(Point2 point2 : d4) {
                Point2 pn = point2.cpy();
                if(offset != null) {
                    pn.add(offset);
                }
                cons.get(pn);
            }
        }
    }
}