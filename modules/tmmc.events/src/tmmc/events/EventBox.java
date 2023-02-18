package tmmc.events;

import arc.struct.ObjectMap;
import arc.struct.Seq;

import java.util.function.Consumer;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class EventBox implements Eventable {
    public final ObjectMap<Class<?>, Seq<Consumer<Object>>> runs = new ObjectMap<>();
    public static PostBuilder<?> builder = PostBuilder.byDefault();
    protected Class<?> current;

    public void set(Class<?> aClass) {
        this.current = aClass;
    }

    public<T> void register(Class<T> tClass, Consumer<T> consumer) {
        this.set(Objects.requireNonNull(tClass));
        this.register(consumer);
    }

    public void registerRun(Class<?> current, Runnable runnable) {
        this.register(current, ignored -> runnable.run());
    }

    public void registerRun(Runnable runnable) {
        this.register(ignored -> runnable.run());
    }

    public<T> void register(Consumer<T> consumer) {
        Objects.requireNonNull(this.current);

        if(!this.runs.containsKey(this.current)) {
            this.runs.put(this.current, new Seq<>());
        }

        this.runs.get(this.current).add((Consumer<Object>) consumer);
    }

    @Override
    public LoadedPost<?> executePost(Object obj) {
        Class<?> aClass = obj == null ? null : obj.getClass();

        if(aClass != null && builder != null) {
            LoadedPost<?> builder = EventBox.builder.build(obj);

            if(runs.containsKey(aClass)) {
                runs.get(aClass).forEach(run -> {
                    try {
                        run.accept(obj);
                        builder.addTotal();
                    } catch(Throwable error) {
                        builder.addTotal(error);
                    }
                });
            }

            builder.afterCreate();
            return builder;
        }

        return null;
    }
}