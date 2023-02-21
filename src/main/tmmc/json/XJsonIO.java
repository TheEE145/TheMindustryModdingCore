package tmmc.json;

import arc.struct.ObjectMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public class XJsonIO {
    private static final ObjectMap<Class<?>, XJsonIOCell<?>> cacheMap = new ObjectMap<>();
    private static final Gson gson;

    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Contract(pure = true)
    public static Gson getGson() {
        return gson;
    }

    public static void clearCache() {
        cacheMap.clear();
    }

    public static<T> T read(String json, Class<T> type) {
        return getCell(type).read(json);
    }

    public static String write(Object object) {
        return gson.toJson(object);
    }

    @Contract("_ -> new")
    public static<T> @NotNull XJsonIOCell<T> getCell(Class<T> type) {
        if(cacheMap.containsKey(type)) {
            return (XJsonIOCell<T>) cacheMap.get(type);
        } else {
            var cell = new XJsonIO.XJsonIOCell<>(type);
            cacheMap.put(type, cell);
            return cell;
        }
    }

    public record XJsonIOCell<T>(Class<T> type) {
        public T read(String json) {
            return gson.fromJson(json, this.type);
        }
    }
}