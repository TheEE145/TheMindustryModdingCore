package tmmc.graphics;

import arc.Events;
import arc.graphics.g2d.TextureRegion;

import org.jetbrains.annotations.NotNull;
import mindustry.game.EventType;
import tmmc.util.TimePeriod;

public class AnimatedTextureRegion extends TextureRegion {
    public final @NotNull TextureRegion[] textures;
    public int frame = 0;

    public AnimatedTextureRegion(TextureRegion[] textures) {
        if(textures != null) {
            this.textures = textures;
        } else {
            this.textures = new TextureRegion[0];
        }
    }

    public AnimatedTextureRegion() {
        this(null);
    }

    public void update() {
        this.set(this.textures[frame++ % this.textures.length]);
    }

    public void runTrigger() {
        Events.run(EventType.Trigger.update, this::update);
    }

    public void runTrigger(float period) {
        if(period <= 0) {
            this.runTrigger();
        }

        Events.run(EventType.Trigger.update, () -> {
            if(TimePeriod.isTimePeriod(period)) {
                this.update();
            }
        });
    }
}

//content 