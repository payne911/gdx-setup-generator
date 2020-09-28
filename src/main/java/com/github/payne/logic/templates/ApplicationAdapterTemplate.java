package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;

public class ApplicationAdapterTemplate extends GdxTemplate {

    public ApplicationAdapterTemplate() {
        super("Project template included simple launchers and an empty `ApplicationAdapter` extension.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils
                .readResourceFileAsString("generator/dynamic/templates/application-adapter.txt");
    }
}
