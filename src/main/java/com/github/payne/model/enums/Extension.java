package com.github.payne.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Extension {
    ASHLEY("ashley"),
    BOX_2_D_LIGHTS("box2d-lights"),
    AI("ai"),
    BOX_2_D("box2d"),
    BULLET("bullet"),
    CONTROLLERS("controllers"),
    FREETYPE("freetype"),
    TOOLS("tools");

    private final String value;
}
