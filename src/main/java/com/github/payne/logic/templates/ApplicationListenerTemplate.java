package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;

public class ApplicationListenerTemplate extends GdxTemplate {

    public ApplicationListenerTemplate() {
        super("Project template included simple launchers and an empty `ApplicationListener` implementation.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils
                .readResourceFileAsString("generator/dynamic/templates/application-listener.txt");
    }
}
