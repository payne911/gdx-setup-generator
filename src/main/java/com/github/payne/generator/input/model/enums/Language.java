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
    KOTLIN("kotlin", "1.3.+"),
    GROOVY("groovy", "3.0.+");

    private final String value;
    private final String defaultVersion;
}
