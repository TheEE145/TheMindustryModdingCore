package tmmc.util;

import arc.Core;
import arc.Files;
import arc.files.Fi;

import mindustry.Vars;
import mindustry.mod.Mods;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * more stable file finder system
 * @author TheEE145
 */
public class FileFinder {
    public static final int PROTOCOL_TREE = 1;
    public static final int PROTOCOL_CORE = 2;
    public static final int PROTOCOL_FILE = 3;
    public static final int PROTOCOL_ROOT = 4;

    public static Mods.LoadedMod modToSearch;

    @Contract(pure = true)
    public static boolean validFile(Fi fi) {
        return fi != null && fi.exists();
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull AllProtocolSearcher getSearcher(String path) {
        return new AllProtocolSearcher(path);
    }

    public static @Nullable Fi findInRoot(Fi root, String path) {
        if(!validFile(root) || StringUtils.isEmpty(path)) {
            return null;
        }

        for(Fi subFile : root.list()) {
            if(subFile.toString().equals(path)) {
                return subFile;
            }

            if(subFile.isDirectory()) {
                Fi theoryFile = findInRoot(subFile, path);

                if(validFile(theoryFile)) {
                    return theoryFile;
                }
            }
        }

        return null;
    }

    public static Fi findByProtocol(int id, String path) {
        return switch(id) {
            case PROTOCOL_TREE -> findByFirstProtocol  (path);
            case PROTOCOL_CORE -> findBySecondProtocol (path);
            case PROTOCOL_FILE -> findByThirdProtocol  (path);
            case PROTOCOL_ROOT -> findByFourthProtocol (path);
            default -> null;
        };
    }

    public static @Nullable Fi findByFourthProtocol(String path) {
        if(modToSearch == null) {
            return null;
        }

        return findInRoot(modToSearch.root, path);
    }

    @Contract(pure = true)
    public static @NotNull Fi findByThirdProtocol(String path) {
        return new Fi(path, Files.FileType.internal);
    }

    public static @Nullable Fi findBySecondProtocol(String path) {
        try {
            Objects.requireNonNull(Core.files);
            return Core.files.get(path, Files.FileType.internal);
        } catch(NullPointerException exception) {
            //if Core.files is null
            return null;
        }
    }

    public static @Nullable Fi findByFirstProtocol(String path) {
        try {
            return Objects.requireNonNull(Vars.tree).get(path);
        } catch(NullPointerException exception) {
            //if #ars.tree is null
            return null;
        }
    }

    public static class AllProtocolSearcher {
        public int currentProtocol = PROTOCOL_TREE;
        private boolean log = false;
        public final String path;

        @Contract(pure = true)
        public AllProtocolSearcher(String path) {
            this.path = path == null ? "" : path;
        }

        public AllProtocolSearcher log() {
            this.log = true;
            return this;
        }

        public Fi nextProtocol() {
            if(this.log) {
                Log.info("finding file \"" + this.path + "\": protocol " + this.currentProtocol);
            }

            Fi fi = findByProtocol(this.currentProtocol, this.path);

            if(validFile(fi)) {
                if(this.log) {
                    Log.info("file found. returning...");
                }

                return fi;
            } else {
                if(currentProtocol == PROTOCOL_ROOT) {
                    if(this.log) {
                        Log.fine("file not found. possible this file doesn't exists");
                    }

                    return null;
                } else {
                    this.currentProtocol++;
                    return this.nextProtocol();
                }
            }
        }
    }
}