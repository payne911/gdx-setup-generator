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
import static com.github.payne.generator.input.model.enums.Template.CLASSIC;

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
import com.github.payne.logic.templates.ClassicTemplate;
import com.github.payne.logic.templates.GdxTemplate;
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
        PLATFORM_MAPPINGS.put(ANDROID, new AndroidModule(ANDROID.getValue()));
        PLATFORM_MAPPINGS.put(CORE, new CoreModule(CORE.getValue()));
        PLATFORM_MAPPINGS.put(DESKTOP_LEGACY, new DesktopModule(DESKTOP_LEGACY.getValue()));
        PLATFORM_MAPPINGS.put(HEADLESS, new HeadlessModule(HEADLESS.getValue()));
        PLATFORM_MAPPINGS.put(HTML, new HtmlModule(HTML.getValue()));
        PLATFORM_MAPPINGS.put(IOS, new IosModule(IOS.getValue()));
        PLATFORM_MAPPINGS.put(LWJGL_3, new Lwjgl3Module(LWJGL_3.getValue()));
        PLATFORM_MAPPINGS.put(SERVER, new ServerModule(SERVER.getValue()));
        PLATFORM_MAPPINGS.put(SHARED, new SharedModule(SHARED.getValue()));
    }

    private static void initTemplates() {
        TEMPLATE_MAPPINGS.put(CLASSIC, new ClassicTemplate());
        // todo: finish the mappings
    }

    public static GdxModule getModule(Platform platform) {
        return PLATFORM_MAPPINGS.get(platform);
    }

    public static GdxTemplate getTemplate(Template template) {
        return TEMPLATE_MAPPINGS.get(template);
    }
}
