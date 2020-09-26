package com.github.payne.generator.input.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Platform {
    ANDROID("android"),
    CORE("core"),
    DESKTOP_LEGACY("desktop"),
    HEADLESS("headless"),
    HTML("html"),
    IOS("ios"),
    LWJGL_3("lwjgl3"),
    SERVER("server"),
    SHARED("shared");

    private final String value;
}
