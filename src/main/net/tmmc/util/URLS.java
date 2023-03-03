package net.tmmc.util;

import arc.Core;
import net.tmmc.json.JsonMod;
import org.jetbrains.annotations.Contract;
import org.apache.commons.validator.UrlValidator;

public class URLS {
    public static final UrlValidator validator = new UrlValidator();

    public static boolean validUrl(String url) {
        return validator.isValid(url);
    }

    @Contract("null -> false")
    public static boolean validGithubUrl(String url) {
        if(url == null) return false;
        return validator.isValid(JsonMod.githubDomain + url);
    }

    public static boolean openUrl(String url) {
        return Core.app.openURI(url);
    }

    public static boolean openGithubUrl(String url) {
        return openUrl(JsonMod.githubDomain + url);
    }

    @Contract("null -> fail")
    public static boolean openGithubUrl(JsonMod.Repository repository) {
        if(repository != null) return openGithubUrl(repository.asUrlFragment());
        throw new NullPointerException("repository mus be not null");
    }
}