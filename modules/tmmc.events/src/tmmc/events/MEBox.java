package tmmc.events;

import arc.Events;
import mindustry.game.EventType;

public class MEBox {
    public static final EventBox box = new EventBox();

    static {
        for(Class<?> uClass : EventType.class.getClasses()) {
            Events.on(uClass, box::executePost);
        }
    }
}