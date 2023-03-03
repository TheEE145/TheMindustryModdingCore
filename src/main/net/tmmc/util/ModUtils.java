package net.tmmc.util;

import arc.math.Mathf;
import arc.util.Strings;

import net.tmmc.json.JsonMod;
import mindustry.mod.Mods.LoadedMod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import static mindustry.Vars.content;
import static mindustry.Vars.mods;

public class ModUtils {
    public static boolean isValidMod(String name) {
        return isValidMod(mods.getMod(name));
    }

    public static boolean hasRepository(LoadedMod mod) {
        return URLS.validGithubUrl(mod == null ? null : mod.getRepo());
    }

    public static void setRepositories() {
        mods.eachEnabled(mod -> {
            if(mod != null && !hasRepository(mod)) {
                var md = JsonMod.create(mod.main, mod);

                var repository = md.getRepository();
                if(repository != null) {
                    mod.setRepo(repository.asUrlFragment());
                } else {
                    String a = md.getAuthor(), n = md.getName();

                    var authors = new String[] {StringUtils.toSpace(a), a,
                            Strings.camelToKebab(a), Strings.kebabToCamel(a)
                    };

                    var names = new String[] {StringUtils.toSpace(n), n,
                            Strings.camelToKebab(n), Strings.kebabToCamel(n)
                    };

                    for(var author : authors) {
                        for(var name : names) {
                            var r = author + "/" + name;
                            if(URLS.validGithubUrl(r)) {
                                mod.setRepo(r);
                            }
                        }
                    }
                }
            }
        });
    }

    @Contract("null -> false")
    public static boolean isValidMod(LoadedMod mod) {
        return mod != null && mod.enabled();
    }

    public static @Nullable LoadedMod getCurrentMod() {
        String testName = Mathf.random(Long.MAX_VALUE) + "";
        String test = content.transformName(testName);
        if(test.equals(testName)) {
            return null;
        } else {
            return mods.getMod(test.substring(0, test.indexOf('-')));
        }
    }
}