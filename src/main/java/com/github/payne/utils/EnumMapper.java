package com.github.payne.utils;

import static com.github.payne.generator.input.model.enums.Platform.ANDROID;
import static com.github.payne.generator.input.model.enums.Platform.CORE;
import static com.github.payne.generator.input.model.enums.Platform.DESKTOP_LEGACY;
import static com.github.payne.generator.input.model.enums.Platform.HEADLESS;
import static com.github.payne.generator.input.model.enums.Platform.HTML;
import static com.github.payne.generator.input.model.enums.Platform.IOS;
import static com.github.payne.generator.input.model.enums.Platform.LWJGL_3;
import static com.github.payne.generator.input.model.enums.Platform.SERVER;
import static com.github.payne.generator.input.model.enums.Platform.SHARED;
import static com.github.payne.generator.input.model.enums.Template.APPLICATION_ADAPTER;
import static com.github.payne.generator.input.model.enums.Template.APPLICATION_LISTENER;
import static com.github.payne.generator.input.model.enums.Template.CLASSIC;
import static com.github.payne.generator.input.model.enums.Template.GAME;
import static com.github.payne.generator.input.model.enums.Template.INPUT_PROCESSOR;
import static com.github.payne.generator.input.model.enums.Template.SCENE_2D;

import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import com.github.payne.logic.LogicProcessor;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.android.AndroidModule;
import com.github.payne.logic.folders.modules.core.CoreModule;
import com.github.payne.logic.folders.modules.desktop.DesktopModule;
import com.github.payne.logic.folders.modules.headless.HeadlessModule;
import com.github.payne.logic.folders.modules.html.HtmlModule;
import com.github.payne.logic.folders.modules.ios.IosModule;
import com.github.payne.logic.folders.modules.lwjgl3.Lwjgl3Module;
import com.github.payne.logic.folders.modules.server.ServerModule;
import com.github.payne.logic.folders.modules.shared.SharedModule;
import com.github.payne.logic.templates.ApplicationAdapterTemplate;
import com.github.payne.logic.templates.ClassicTemplate;
import com.github.payne.logic.templates.GameTemplate;
import com.github.payne.logic.templates.InputProcessorTemplate;
import com.github.payne.logic.templates.Scene2dTemplate;
import com.github.payne.logic.templates.base.GdxTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * The bridge between the input of the user and the associated implementation in the generator.
 * <p>
 * Used to keep the {@code generator} package's model free from dependencies on the {@code logic}
 * package. Moreover, used to abstract away this mapping from the {@link LogicProcessor}.
 */
public final class EnumMapper {

    private static final Map<Platform, GdxModule> PLATFORM_MAPPINGS = new HashMap<>();
    private static final Map<Template, GdxTemplate> TEMPLATE_MAPPINGS = new HashMap<>();

    static {
        initModules();
        initTemplates();
    }

    private static void initModules() {
        PLATFORM_MAPPINGS.put(ANDROID, new AndroidModule(ANDROID.getString()));
        PLATFORM_MAPPINGS.put(CORE, new CoreModule(CORE.getString()));
        PLATFORM_MAPPINGS.put(DESKTOP_LEGACY, new DesktopModule(DESKTOP_LEGACY.getString()));
        PLATFORM_MAPPINGS.put(HEADLESS, new HeadlessModule(HEADLESS.getString()));
        PLATFORM_MAPPINGS.put(HTML, new HtmlModule(HTML.getString()));
        PLATFORM_MAPPINGS.put(IOS, new IosModule(IOS.getString()));
        PLATFORM_MAPPINGS.put(LWJGL_3, new Lwjgl3Module(LWJGL_3.getString()));
        PLATFORM_MAPPINGS.put(SERVER, new ServerModule(SERVER.getString()));
        PLATFORM_MAPPINGS.put(SHARED, new SharedModule(SHARED.getString()));
    }

    private static void initTemplates() {
        TEMPLATE_MAPPINGS.put(APPLICATION_ADAPTER, new ApplicationAdapterTemplate());
        TEMPLATE_MAPPINGS.put(APPLICATION_LISTENER, new ApplicationAdapterTemplate());
        TEMPLATE_MAPPINGS.put(CLASSIC, new ClassicTemplate());
        TEMPLATE_MAPPINGS.put(GAME, new GameTemplate());
        TEMPLATE_MAPPINGS.put(INPUT_PROCESSOR, new InputProcessorTemplate());
        TEMPLATE_MAPPINGS.put(SCENE_2D, new Scene2dTemplate());
    }

    public static GdxModule getModule(Platform platform) {
        return PLATFORM_MAPPINGS.get(platform);
    }

    public static GdxTemplate getTemplate(Template template) {
        return TEMPLATE_MAPPINGS.get(template);
    }
}
