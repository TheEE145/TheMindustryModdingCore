package tmmc.events;

import org.jetbrains.annotations.Contract;
import tmmc.util.RescaledArray;

public abstract class AbstractLoadedPost<T> implements LoadedPost<T> {
    protected final RescaledArray<Throwable> causes = new RescaledArray<>();
    protected final T event;
    protected int total = 0;

    @Contract(pure = true)
    public AbstractLoadedPost(T event) {
        this.event = event;
    }

    @Override
    public void addTotal() {
        this.addTotal(null);
    }

    @Override
    public void addTotal(Throwable cause) {
        this.total++;

        if(cause != null) {
            this.causes.expand(1);
            this.causes.editLast(cause);
        }
    }

    @Override
    public T getEvent() {
        return this.event;
    }

    @Override
    public int totalCalls() {
        return this.total;
    }

    @Override
    public RescaledArray<Throwable> causes() {
        return this.causes;
    }
}