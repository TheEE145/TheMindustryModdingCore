package net.tmmc.util;

import mindustry.game.EventType.ClientLoadEvent;
import mindustry.ctype.UnlockableContent;

import net.tmmc.annotations.inteji.XJsonField;
import net.tmmc.json.JsonMod;

import org.jetbrains.annotations.Contract;
import java.util.Objects;

import arc.util.Time;
import arc.func.Cons;
import arc.Events;

public class BundleCreator {
    @XJsonField private String[] ignoredToScan;
    @XJsonField private ScanType scanType;
    @XJsonField private boolean enabled;
    @XJsonField private float postTime;

    public void start(JsonMod mod) {
        Events.on(ClientLoadEvent.class, this.getShell(mod));
    }

    public Cons<ClientLoadEvent> getShell(JsonMod mod) {
        return ignored -> {
            if(this.enabled && mod != null) {
                Runnable runnable = this.getRuntimeShell(mod);
                if(this.postTime <= 0) {
                    runnable.run();
                } else {
                    Time.runTask(this.postTime, runnable);
                }
            }
        };
    }

    public Runnable getRuntimeShell(JsonMod mod) {
        return () -> {
            if(mod != null) {
                Content.each(content -> {
                    if(content != null && this.accept(content, mod)) {
                        try {
                            String name = content.name.substring(content.minfo.mod.name.length() + 1);
                            content.localizedName = StringUtils.toSpace(name);
                        } catch(Throwable ignored) {
                            content.localizedName = StringUtils.toSpace(content.name);
                        }
                    }
                });
            }
        };
    }

    public boolean accept(UnlockableContent content, JsonMod mod) {
        if(content == null || mod == null) {
            return false;
        }

        for(String str : this.ignoredToScan) {
            if(Objects.equals(str, content.name)) {
                return false;
            }
        }

        return this.scanType == null || this.scanType.accept(content, mod);
    }

    public record ScanType(boolean local, boolean mods, boolean named) {
        @Contract("null, _ -> false; !null, null -> false")
        public boolean accept(UnlockableContent content, JsonMod mod) {
            if(content == null || mod == null) {
                return false;
            }

            boolean local = this.local;
            boolean mods = this.mods;
            boolean named = this.named;

            if(local && mods && named) {
                return true;
            }

            if(!local && !mods && !named) {
                return false;
            }

            boolean isLocal;
            try {
                isLocal = content.minfo.mod == mod.getRaw();
            } catch(NullPointerException ignored) {
                isLocal = false;
            }
            boolean isModded = !content.isVanilla();
            boolean isNamed = Bundles.hasName(content);

            boolean b = named == isNamed;
            return (isLocal) ? (local && b) : ((isModded) ? (mods && b) : (b));
        }
    }
}