package tmmc.util;

import arc.util.ColorCodes;
import arc.struct.Seq;
import arc.util.OS;
import arc.Events;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import mindustry.game.EventType;

import java.io.IOException;
import java.io.Writer;

import static arc.Core.*;
import static mindustry.Vars.*;

/**
 * hacks mindustry logging system to add custom log levels like fine or fatal
 */
public class Log {
    public static String logPrefix = "anonymous";
    public static String color = "cyan";

    @Contract(pure = true)
    private static @NotNull String prefix() {
        return "[" + color + "][" + logPrefix + "][] ";
    }

    public static void log(LogLevel level, String  str, Object... args) {
        handler.log(level, prefix() + arc.util.Log.format(str, args));
    }

    public static void info(String str, Object... args) {
        log(LogLevel.INFO, str, args);
    }

    public static void warning(String str, Object... args) {
        log(LogLevel.WARNING, str, args);
    }

    public static void error(String str, Object... args) {
        log(LogLevel.ERROR, str, args);
    }

    public static void debug(String str, Object... args) {
        log(LogLevel.DEBUG, str, args);
    }

    public static void fine(String str, Object... args) {
        log(LogLevel.FINE, str, args);
    }

    public static void fatal(String str, Object... args) {
        log(LogLevel.FATAL, str, args);
    }

    public record LogLevel(String name, String color) {
        public static final LogLevel FATAL   = new LogLevel("fatal",   "red");
        public static final LogLevel ERROR   = new LogLevel("error",   "red");
        public static final LogLevel FINE    = new LogLevel("fine",    "orange");
        public static final LogLevel WARNING = new LogLevel("warning", "yellow");
        public static final LogLevel DEBUG   = new LogLevel("debug",   "gray");
        public static final LogLevel INFO    = new LogLevel("info",    "blue");

        public @NotNull String format() {
            return "[" + this.color + "][" + name.substring(0, 1).toUpperCase() + "][]";
        }

        public @NotNull String format(String text) {
            return this.format() + " " + text;
        }
    }

    public interface LogHandler {
        void log(LogLevel level, String text);
    }

    public static void tryToLoadWritter() {
        try {
            writer = settings.getDataDirectory()
                    .child("last_log.txt").writer(true);
        } catch(Exception ignored) {
        }
    }

    static {
        tryToLoadWritter();
        //if not loads when load when prints first message
    }

    public static Writer writer;
    public static final Seq<String> logBuffer = new Seq<>();
    public static final LogHandler handler = (level, text) -> {
        synchronized(logBuffer) {
            String result = text;
            System.out.println(arc.util.Log.format(text));
            result = level.format(result);

            if(!headless && (ui == null || ui.consolefrag == null)) {
                logBuffer.add(result);
            }else if(!headless) {
                if(!OS.isWindows) {
                    for(String code : ColorCodes.values) {
                        result = result.replace(code, "");
                    }
                }

                ui.consolefrag.addMessage(arc.util.Log.removeColors(result));
            }

            if(writer == null) {
                tryToLoadWritter();
            }

            if(writer != null) {
                try {
                    writer.write("[" + Character.toUpperCase(level.name().charAt(0)) + "] " +
                            arc.util.Log.removeColors(result) + "\n");

                    writer.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                    //ignore it
                }
            }
        }
    };

    static {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            logBuffer.each(ui.consolefrag::addMessage);
        });
    }
}