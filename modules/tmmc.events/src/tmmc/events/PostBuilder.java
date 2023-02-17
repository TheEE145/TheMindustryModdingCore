package tmmc.events;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public interface PostBuilder<T extends LoadedPost<?>> {
    T build(Object event);

    @Contract(value = " -> new", pure = true)
    static<T> @NotNull PostBuilder<LoadedPost<T>> byDefault() {
        return new PostBuilder<>() {
            @Override
            public LoadedPost<T> build(Object event) {
                return (LoadedPost<T>) new DefaultLoadedPost<>(event);
            }
        };
    }
}