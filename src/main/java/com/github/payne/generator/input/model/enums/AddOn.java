package com.github.payne.generator.input.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum AddOn {
    README("readme"),
    GUI_ASSETS("gui-assets"),
    GRADLE_WRAPPER("gradle-wrapper");

    private final String string;

    public static AddOn fromString(String text) {
        for (AddOn x : AddOn.values()) {
            if (x.string.equalsIgnoreCase(text)) {
                return x;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
