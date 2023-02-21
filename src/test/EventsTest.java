import mindustry.game.EventType.ClientLoadEvent;
import net.tmmc.util.events.Events;
import arc.util.Log;

public class EventsTest {
    public static void main(String[] args) {
        Events.currentEvent = ClientLoadEvent.class;

        //Events.postDataRun = data -> {
        //    if(data.hasErrors()) {
        //        for(Throwable cause : data.causes()) {
        //            Log.err(cause);
        //        }
        //    }
        //};

        Events.<ClientLoadEvent>register(Log::info);
        Events.register(() -> {
            Log.info("All Guns top!");
        });

        Events.register(() -> {
            throw new RuntimeException("i know you");
        });

        Events.post(new ClientLoadEvent());
    }
}