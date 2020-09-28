package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;

public class InputProcessorTemplate extends GdxTemplate {

    public InputProcessorTemplate() {
        super("Project template included simple launchers and an empty `ApplicationListener` implementation, that also listened to user input.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils
                .readResourceFileAsString("generator/dynamic/templates/input-processor.txt");
    }
}
