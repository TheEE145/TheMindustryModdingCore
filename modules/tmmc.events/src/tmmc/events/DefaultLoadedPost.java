package tmmc.events;

public class DefaultLoadedPost<T> extends AbstractLoadedPost<T> {
    public DefaultLoadedPost(T event) {
        super(event);
    }

    @Override
    public void afterCreate() {
        this.throwCauses();
    }
}