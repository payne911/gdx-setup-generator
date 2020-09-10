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
    EMPTY("empty"),
    GAME("game"),
    INPUT_PROCESSOR("input-processor"),
    SCENE_2D("scene2d"),
    AUTUMN_MVC_BASIC("autumn-mvc-basic"),
    AUTUMN_MVC_BOX_2D_VIS_UI("autumn-mvc-box2d-visui"),
    AUTUMN_MVC_VIS_UI("autumn-mvc-visui"),
    KIWI_INPUT_AWARE_APPLICATION_LISTENER("kiwi-input-aware-application-listener"),
    KIWI_ABSTRACT_APPLICATION_LISTENER("kiwi-abstract-application-listener"),
    LML_BASIC("lml-basic"),
    NOISE_4J("noise4j"),
    VIS_UI_BASIC("visui-basic"),
    VIS_UI_SHOWCASE("visui-showcase"),
    WEBSOCKET_BASIC("websocket-basic");

    private final String value;
}
