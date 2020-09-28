package com.github.payne.generator.input.model.enums;

import com.github.payne.generator.input.GeneratorConfigs;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Extensions are official third-party libraries supported by libGDX itself.
 * <p>
 * You can use {@link LibraryVersion#GDX_VERSION} as the version and it will inject whatever version
 * you have chosen as the libGDX version ({@link GeneratorConfigs#getLibGdxVersion()}).
 */
@Getter
@ToString
@RequiredArgsConstructor
public enum Extension {
    ASHLEY("ashley", "1.7.3"),
    BOX_2_D_LIGHTS("box2d-lights", "1.5"),
    AI("ai", "1.8.2"),
    BOX_2_D("box2d", LibraryVersion.GDX_VERSION),
    BULLET("bullet", LibraryVersion.GDX_VERSION),
    CONTROLLERS("controllers", LibraryVersion.GDX_VERSION),
    FREETYPE("freetype", LibraryVersion.GDX_VERSION),
    TOOLS("tools", LibraryVersion.GDX_VERSION);

    private final String id;
    private final String propertyVersion;


    public static final class LibraryVersion {

        private LibraryVersion() {
        }

        /**
         * Use this when you want a library to have exactly the same version string as the selected
         * libGDX version (which comes from {@link GeneratorConfigs#getLibGdxVersion()}.
         */
        public static final String GDX_VERSION = "gdx";
    }

}
