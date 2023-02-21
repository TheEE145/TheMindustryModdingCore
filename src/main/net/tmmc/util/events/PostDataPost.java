package net.tmmc.util.events;

public interface PostDataPost {
    void run(PostData data);

    PostDataPost def = (data) -> {
        if(data.hasErrors()) {
            data.invoke();
        }
    };
}