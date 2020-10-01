package com.github.payne.generator.input.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Language {
    JAVA("java", "1.8"),
    SCALA("scala", "2.13.+"),
    KOTLIN("kotlin", "1.4.+"),
    GROOVY("groovy", "3.0.+");

    private final String string;
    private final String defaultVersion;

    public static Language fromString(String text) {
        for (Language x : Language.values()) {
            if (x.string.equalsIgnoreCase(text)) {
                return x;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
