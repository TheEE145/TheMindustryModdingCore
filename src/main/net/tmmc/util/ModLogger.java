package net.tmmc.util;

import arc.util.Log;
import arc.util.Log.LogLevel;

import net.tmmc.json.JsonMod;
import org.jetbrains.annotations.NotNull;

public record ModLogger(JsonMod mod) {
    public void log(LogLevel level, String message, Object... args) {
        Log.log(level, prefixTo(message), args);
    }

    public void info(String message, Object... args) {
        log(LogLevel.info, message, args);
    }

    public void warn(String message, Object... args) {
        log(LogLevel.warn, message, args);
    }

    public void err(String message, Object... args) {
        log(LogLevel.err, message, args);
    }

    public void debug(String message, Object... args) {
        log(LogLevel.debug, message, args);
    }

    public void user(String message, Object... args) {
        log(LogLevel.none, message, args);
    }

    public void log(LogLevel level, Object arg) {
        log(level, arg == null ? "null" : arg.toString(), (Object) null);
    }

    public void info(Object arg) {
        log(LogLevel.info, arg);
    }

    public void warn(Object arg) {
        log(LogLevel.warn, arg);
    }

    public void err(Object arg) {
        log(LogLevel.err, arg);
    }

    public void debug(Object arg) {
        log(LogLevel.debug, arg);
    }

    public void user(Object arg) {
        log(LogLevel.none, arg);
    }

    public void err(Throwable throwable) {
        err(ThrowableUtils.toString(throwable));
    }

    public @NotNull String prefixTo(String message) {
        return this.mod.getPrefix() + " " + message;
    }
}
