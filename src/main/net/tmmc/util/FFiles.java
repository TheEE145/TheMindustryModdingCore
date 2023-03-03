package net.tmmc.util;

import arc.files.Fi;
import arc.func.Cons;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FFiles {
    @Contract("null -> false")
    public static boolean validFile(Fi fi) {
        return fi != null && fi.exists();
    }

    @Contract("null -> null")
    public static String transformPathStart(String path) {
        if(path == null) return null;
        if(path.startsWith("/") || path.startsWith("\\")) {
            return path.substring(1);
        }

        return path;
    }

    @Contract("null -> null")
    public static String transformPathEnd(String path) {
        if(path == null) return null;
        if(path.endsWith("/") || path.endsWith("\\")) {
            return path.substring(0, path.length() - 1);
        }

        return path;
    }

    public static void eachInAll(@NotNull Fi root, Cons<Fi> file) {
        for(Fi fi : root.list()) {
            file.get(fi);

            if(fi.isDirectory()) {
                eachInAll(fi, file);
            }
        }
    }
}