package tmmc.json;

import arc.struct.Seq;
import arc.files.Fi;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import mindustry.mod.Mods;
import java.util.Arrays;

public class ModJsonData {
    @Contract("null -> null")
    public static ModJsonData from(Mods.LoadedMod mod) {
        return mod == null ? null : from(mod.root.child("mod.json"));
    }

    @Contract("null -> null")
    public static ModJsonData from(Fi fi) {
        if(fi != null && fi.exists()) {
            return from(fi.readString("UTF-8"));
        } else {
            return null;
        }
    }

    public static @NotNull ModJsonData from(String json) {
        ModJsonData data = XGson.gson.fromJson(json, ModJsonData.class);

        if(data.bundleCreation != null) {
            data.bundleCreation.setRoot(data);
        }

        return data;
    }

    private String name;
    private String main;
    private String version;
    private Repository repository;
    private Contributor[] contributors;
    private BundleCreation bundleCreation;

    public BundleCreation getBundleCreation() {
        return this.bundleCreation;
    }

    public Repository getRepository() {
        return this.repository;
    }

    public String getName() {
        return this.name;
    }

    public String getMain() {
        return this.main;
    }

    public String getVersion() {
        return this.version;
    }

    public Seq<Contributor> getContributors() {
        return this.contributors == null ? new Seq<>() : Seq.with(this.contributors);
    }

    public String toJson() {
        return XGson.gson.toJson(this);
    }

    @Override
    public String toString() {
        return "ModJsonData{" +
                "name='" + name + '\'' +
                ", main='" + main + '\'' +
                ", version='" + version + '\'' +
                ", contributors=" + Arrays.toString(contributors) +
                ", repository=" + repository +
                ", bundleCreation=" + bundleCreation +
                '}';
    }
}