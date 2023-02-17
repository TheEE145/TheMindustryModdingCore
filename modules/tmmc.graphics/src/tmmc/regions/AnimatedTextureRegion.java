package tmmc.regions;

import arc.Events;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.EventType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * this region can be animated
 * @author TheEE145
 */
public class AnimatedTextureRegion extends TextureRegion {
    public TextureRegion[] frames;
    public int maxTick = 4;

    private int currentFrame = 0;
    private int tick;

    public static @NotNull AnimatedTextureRegion load(String prefix) {
        return load(prefix, ignored -> {});
    }

    public static @NotNull AnimatedTextureRegion load(
            String prefix, Consumer<AnimatedTextureRegion> init)
    {
        int frames = 1;
        while(Regions.hasRegion(prefix + frames)) {
            frames++;
        }

        frames--;
        AnimatedTextureRegion reg = new AnimatedTextureRegion();
        reg.frames = new TextureRegion[frames];

        for(int i = 0; i < frames;) {
            reg.frames[i] = Regions.getRegion(prefix + (++i));
        }

        init.accept(reg);
        return reg;
    }

    public void nextFrame() {
        this.set(this.frames[currentFrame]);

        this.currentFrame++;
        if(this.currentFrame == this.frames.length) {
            this.currentFrame = 0;
        }
    }

    public void update() {
        if(this.frames != null && --this.tick <= 0) {
            this.tick = maxTick;
            this.nextFrame();
        }
    }

    public AnimatedTextureRegion updateNext() {
        Events.run(EventType.Trigger.update, this::update);
        return this;
    }
}