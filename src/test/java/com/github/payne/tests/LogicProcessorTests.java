package com.github.payne.tests;

import static org.junit.Assert.assertEquals;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.root.DynamicFile;
import com.github.payne.logic.root.files.SettingsGradleFile;
import org.junit.Test;

public class LogicProcessorTests {

    @Test
    public void settingsGradleFile_onePlatform() {
        GeneratorConfigs input = new GeneratorConfigs();
        input.getPlatforms().add(Platform.CORE);

        DynamicFile modules = new SettingsGradleFile(input);
        assertEquals("include core", modules.getContent());
    }

    @Test
    public void settingsGradleFile_twoPlatforms() {
        GeneratorConfigs input = new GeneratorConfigs();
        input.getPlatforms().add(Platform.SERVER);
        input.getPlatforms().add(Platform.LWJGL_3);

        DynamicFile modules = new SettingsGradleFile(input);
        assertEquals("include lwjgl3, server", modules.getContent());
    }

    @Test
    public void settingsGradleFile_threePlatforms() {
        GeneratorConfigs input = new GeneratorConfigs();
        input.getPlatforms().add(Platform.HEADLESS);
        input.getPlatforms().add(Platform.ANDROID);
        input.getPlatforms().add(Platform.IOS);

        DynamicFile modules = new SettingsGradleFile(input);
        assertEquals("include android, headless, ios", modules.getContent());
    }
}
