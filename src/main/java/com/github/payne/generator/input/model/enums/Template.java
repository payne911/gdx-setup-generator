package com.github.payne.generator.input.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Template {

    APPLICATION_ADAPTER("application-adapter"),
    APPLICATION_LISTENER("application-listener"),
    CLASSIC("classic"),
    GAME("game"),
    INPUT_PROCESSOR("input-processor"),
    SCENE_2D("scene2d");

    private final String string;

    public static Template fromString(String text) {
        for (Template x : Template.values()) {
            if (x.string.equalsIgnoreCase(text)) {
                return x;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
