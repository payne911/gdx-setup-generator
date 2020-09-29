package com.github.payne.generator.input.model.enums;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.LibGdxVersion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Extensions are official third-party libraries supported by libGDX itself.
 * <p>
 * You can use {@code LibGdxVersion.GRADLE_PROPERTIES_PREFIX} as the version and it will inject
 * whatever version you have chosen as the libGDX version ({@link GeneratorConfigs#getLibGdxVersion()}).
 *
 * @see LibGdxVersion#GRADLE_PROPERTIES_PREFIX
 */
@Getter
@ToString
@RequiredArgsConstructor
@Deprecated
public enum LegacyExtension {
    ASHLEY("ashley", "1.7.3"),
    BOX_2_D_LIGHTS("box2d-lights", "1.5"),
    AI("ai", "1.8.2"),
    BOX_2_D("box2d", LibGdxVersion.GRADLE_PROPERTIES_PREFIX),
    BULLET("bullet", LibGdxVersion.GRADLE_PROPERTIES_PREFIX),
    CONTROLLERS("controllers", LibGdxVersion.GRADLE_PROPERTIES_PREFIX),
    FREETYPE("freetype", LibGdxVersion.GRADLE_PROPERTIES_PREFIX),
    TOOLS("tools", LibGdxVersion.GRADLE_PROPERTIES_PREFIX);

    private final String id;
    private final String propertyVersion;
}
