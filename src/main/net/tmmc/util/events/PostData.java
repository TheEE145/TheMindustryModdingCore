package net.tmmc.util.events;

import arc.struct.Seq;
import arc.util.ArcRuntimeException;
import org.jetbrains.annotations.Contract;

public record PostData(Seq<Throwable> causes, int launches) {
    @Contract(pure = true)
    public int errorLaunches() {
        if(this.causes == null) return 0;
        return this.causes.size;
    }

    @Contract(pure = true)
    public int nonErrorLaunches() {
        return this.launches - this.errorLaunches();
    }

    @Contract(pure = true)
    public boolean hasErrors() {
        return this.errorLaunches() > 0;
    }

    @Contract(pure = true)
    public boolean isErrorState() {
        return this.nonErrorLaunches() < 0;
    }

    @Contract(value = " -> fail", pure = true)
    public void invoke() {
        throw new ArcRuntimeException(this.causes.get(0));
    }
}