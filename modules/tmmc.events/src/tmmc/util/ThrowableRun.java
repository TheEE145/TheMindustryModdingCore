package tmmc.util;

import org.jetbrains.annotations.Contract;

public class ThrowableRun implements Runnable {
    public final RescaledArray<Throwable> throwables;
    public int index = 0;

    @Contract(pure = true)
    public ThrowableRun(Throwable[] throwables) {
        this(new RescaledArray<>(throwables));
    }

    @Contract(pure = true)
    public ThrowableRun(RescaledArray<Throwable> throwables) {
        this.throwables = throwables;
    }

    public void thr() {
        throw new RuntimeException(this.throwables.get(this.index));
    }

    @Override
    public void run() {
        if(this.throwables == null || this.throwables.length() == 0) {
            return;
        }

        this.thr();
        if(this.index++ < this.throwables.length() - 1) {
            this.run();
        }
    }
}