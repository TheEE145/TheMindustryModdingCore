package net.tmmc.util.events;

import arc.func.Cons;
import arc.struct.Seq;

public interface EventsRunner {
    PostData run(Seq<Cons<Object>> runs, Object obj);

    EventsRunner def = (runs, obj) -> {
        Seq<Throwable> causes = new Seq<>();

        for(Cons<Object> run : runs) {
            try {
                run.get(obj);
            } catch(Throwable any) {
                causes.add(any);
            }
        }

        return new PostData(causes, runs.size);
    };
}