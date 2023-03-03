package net.tmmc.util;

import net.tmmc.json.JsonMod;

public class BaseEvents {
    public @Deprecated record ModContentLoad(JsonMod mod) {};
    public record ModInitEvent(JsonMod mod) {};
}