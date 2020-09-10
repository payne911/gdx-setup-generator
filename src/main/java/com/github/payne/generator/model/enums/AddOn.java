package com.github.payne.generator.model.enums;

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

    private final String value;
}
