package com.github.payne.utils;

import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.input.GeneratorConfigs;

@NotTested
public final class VersionUtils {

    private VersionUtils() {
    }

    /**
     * @param gdxVersion should be the content of {@link GeneratorConfigs#getLibGdxVersion()}
     * @return the gwt version
     */
    public static String deduceGwtVersion(String gdxVersion) {
        String gwtVersion;
        if (gdxVersion.length() == 5 && gdxVersion.charAt(4) != '9') {
            if (gdxVersion.charAt(4) < '5') {
                gwtVersion = "2.6.1";
            } else {
                gwtVersion = "2.8.0";
            }
        } else {
            gwtVersion = "2.8.2";
        }
        return gwtVersion;
    }
}
