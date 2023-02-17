package tmmc.events;

public interface Eventable {
    LoadedPost<?> executePost(Object obj);
}