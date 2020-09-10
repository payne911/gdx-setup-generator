package com.github.payne.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Language {
    SCALA("scala"),
    KOTLIN("kotlin"),
    GROOVY("groovy");

    private final String value;
}
