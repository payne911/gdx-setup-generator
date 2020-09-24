package com.github.payne.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import org.junit.Test;

public class GeneratorConfigsTests {

    @Test
    public void contains_Platform() {
        GeneratorConfigs configs = new GeneratorConfigs();
        configs.getPlatforms().add(Platform.CORE);
        configs.getPlatforms().add(Platform.HTML);

        assertTrue(configs.contains(Platform.CORE));
        assertTrue(configs.contains(Platform.HTML));
        assertFalse(configs.contains(Platform.ANDROID));
    }

    @Test
    public void contains_AddOn() {
        GeneratorConfigs configs = new GeneratorConfigs();
        configs.getAddOns().add(AddOn.GRADLE_WRAPPER);
        configs.getAddOns().add(AddOn.GUI_ASSETS);

        assertTrue(configs.contains(AddOn.GRADLE_WRAPPER));
        assertTrue(configs.contains(AddOn.GUI_ASSETS));
        assertFalse(configs.contains(AddOn.README));
    }

    @Test
    public void contains_Language() {
        GeneratorConfigs configs = new GeneratorConfigs();
        configs.getLanguages().add(new VersionedLanguage(Language.JAVA));
        configs.getLanguages().add(new VersionedLanguage(Language.KOTLIN));

        assertTrue(configs.contains(Language.JAVA));
        assertTrue(configs.contains(Language.KOTLIN));
        assertFalse(configs.contains(Language.GROOVY));
    }
}
