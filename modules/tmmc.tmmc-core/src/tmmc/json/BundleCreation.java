package tmmc.json;

import arc.Events;
import arc.util.Time;
import arc.struct.Seq;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ClientLoadEvent;
import tmmc.util.ContentLoad;
import tmmc.util.StringUtils;

public class BundleCreation {
    private transient ModJsonData root;

    private final ScanType scanType = new ScanType(false, true, false);
    private final String[] ignoredToScan = new String[0];
    private final boolean enabled = false;
    private final float postTime = 0;

    @SuppressWarnings("all") //idea warnings again
    public Consumer<ClientLoadEvent> getRuntime() {
        return ignored -> {
            if(this.enabled) {
                Runnable run = this.getPostRuntime();

                if(postTime == 0) {
                    run.run();
                } else {
                    Time.runTask(this.postTime, run);
                }
            }
        };
    }

    public boolean needChange(UnlockableContent content) {
        for(String str : this.ignoredToScan) {
            if(Objects.equals(str, content.name)) {
                return false;
            }
        }

        return this.scanType.needChange(content, this.root);
    }

    public Runnable getPostRuntime() {
        return () -> {
            ContentLoad.eachUnlockable(cont -> {
                if(this.needChange(cont)) {
                    String name = cont.name;
                    name = name.substring(name.indexOf('-'));
                    cont.localizedName = StringUtils.anyToSpace(name);
                }
            });
        };
    }

    public Seq<String> getIgnoredToScan() {
        return Seq.with(this.ignoredToScan);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getPostTime() {
        return this.postTime;
    }

    public ScanType getScanType() {
        return this.scanType;
    }

    public ModJsonData getRoot() {
        return this.root;
    }

    public void setRoot(ModJsonData data) {
        if(this.root != null) {
            throw new IllegalArgumentException("data already set");
        } else {
            this.root = data;
        }
    }

    @Override
    public String toString() {
        return "BundleCreation{" +
                "scanType=" + scanType +
                ", ignoredToScan=" + Arrays.toString(ignoredToScan) +
                ", enabled=" + enabled +
                ", postTime=" + postTime +
                '}';
    }
}