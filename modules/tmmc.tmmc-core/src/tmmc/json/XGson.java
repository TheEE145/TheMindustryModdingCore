package tmmc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class XGson {
    public static final GsonBuilder builder;
    public static final Gson gson;

    static {
        builder = new GsonBuilder().setPrettyPrinting();
        gson = builder.create();
    }
}