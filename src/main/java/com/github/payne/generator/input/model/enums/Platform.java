package com.github.payne.generator.input.model.enums;

import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.core.CoreModule;
import com.github.payne.logic.modules.server.ServerModule;
import com.github.payne.logic.modules.shared.SharedModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Platform {
    // todo: ensure all Modules are linked to their appropriate platform! (remove placeholders)
    ANDROID("android") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    CORE("core") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    DESKTOP_LEGACY("desktop") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    HEADLESS("headless") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    HTML("html") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    IOS("ios") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    LWJGL_3("lwjgl3") {
        @Override
        public GdxModule getModuleGenerator() {
            return new CoreModule(getValue());
        }
    },
    SERVER("server") {
        @Override
        public GdxModule getModuleGenerator() {
            return new ServerModule(getValue());
        }
    },
    SHARED("shared") {
        @Override
        public GdxModule getModuleGenerator() {
            return new SharedModule(getValue());
        }
    };

    private final String value;

    public abstract GdxModule getModuleGenerator();
}
