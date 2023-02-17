package tmmc.events;

import tmmc.util.RescaledArray;
import tmmc.util.ThrowableRun;

public interface LoadedPost<T> {
    T getEvent();

    int totalCalls();
    void afterCreate();
    RescaledArray<Throwable> causes();

    void addTotal();
    void addTotal(Throwable cause);

    default void throwCauses() {
        new ThrowableRun(this.causes()).run();
    }

    default int errorCalls() {
        return this.causes().length();
    }

    default boolean isBrokenEvent() {
        return this.notErroredCalls() < 0;
    }

    default int notErroredCalls() {
        return this.totalCalls() - this.errorCalls();
    }

    default boolean isErrored() {
        return this.errorCalls() > 0;
    }

    default boolean empty() {
        return this.totalCalls() == 0;
    }

    default Class<?> getEventClass() {
        T event = this.getEvent();
        return event == null ? null : event.getClass();
    }
}