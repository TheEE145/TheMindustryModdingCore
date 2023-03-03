package net.tmmc.util.events;

import arc.func.Cons;
import arc.struct.Seq;
import arc.struct.ObjectMap;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.game.EventType.Trigger;
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unchecked")
public class Events {
    public static ObjectMap<Class<?>, Seq<Cons<Object>>> events = new ObjectMap<>();
    public static Class<?> currentEvent = null;

    public static EventsRunner eventsRunner = EventsRunner.def;
    public static PostDataPost postDataRun = PostDataPost.def;

    public static void register(Class<?> tClass, Runnable runnable) {
        register(tClass, ignored -> runnable.run());
    }

    public static<T> void register(Class<T> tClass, Cons<T> handler) {
        if(!events.containsKey(tClass)) {
            events.put(tClass, new Seq<>());
        }

        events.get(tClass).add((Cons<Object>) handler);
    }

    public static<T> void register(Class<T> tClass, int interval, Cons<T> handler) {
        register(tClass, (e) -> Time.runTask(interval, () -> handler.get(e)));
    }

    public static void register(Class<?> aClass, int interval, Runnable runnable) {
        register(aClass, interval, ignored -> runnable.run());
    }

    public static<T> void register(int interval, Cons<T> handler) {
        register(currentEvent, interval, obj -> handler.get((T) obj));
    }

    public static void register(int interval, Runnable runnable) {
        register(currentEvent, interval, runnable);
    }

    public static void register(Runnable runnable) {
        register(currentEvent, runnable);
    }

    public static<T> void register(Cons<T> handler) {
        register(currentEvent, obj -> handler.get((T) obj));
    }

    public static PostData post(Object object) {
        var aClass = object == null ? null : object.getClass();
        if(aClass != null && events.containsKey(aClass)) {
            var eventsList = events.get(aClass);
            if(eventsList != null) {
                PostData data = eventsRunner.run(eventsList, object);
                postDataRun.run(data);
                return data;
            }
        }

        PostData data = new PostData(new Seq<>(), 0);
        postDataRun.run(data);
        return data;
    }

    public static void clear(Class<?> tClass) {
        if(events.containsKey(tClass)) {
            events.get(tClass).clear();
        }
    }

    public static void clear() {
        events.clear();
    }

    static {
        for(Class<?> eClass : EventType.class.getClasses()) {
            arc.Events.on(eClass, Events::post);
        }

        for(Trigger trigger : Trigger.values()) {
            arc.Events.run(trigger, () -> {
                Events.post(new TriggerEvent(trigger));
            });
        }
    }

    public static class TriggerEvent {
        public Trigger trigger;

        @Contract(pure = true)
        public TriggerEvent(Trigger trigger) {
            this.trigger = trigger;
        }

        @Contract(pure = true)
        public boolean is(Trigger trigger) {
            return this.trigger == trigger;
        }

        @Contract(pure = true)
        public boolean update() {
            return this.is(Trigger.update);
        }

        @Contract(pure = true)
        public boolean drawStart() {
            return this.is(Trigger.preDraw);
        }

        @Contract(pure = true)
        public boolean draw() {
            return this.is(Trigger.draw);
        }

        @Contract(pure = true)
        public boolean drawEnd() {
            return this.is(Trigger.postDraw);
        }

        @Override
        public String toString() {
            Trigger trigger = this.trigger;
            return trigger == null ? "null" : trigger.name();
        }

        @Override
        @Contract(value = "null -> false", pure = true)
        public boolean equals(Object obj) {
            if(obj == this) return true;

            if(obj instanceof TriggerEvent) {
                return ((TriggerEvent) obj).is(this.trigger);
            }

            return false;
        }
    }
}