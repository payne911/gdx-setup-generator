package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;

public class Scene2dTemplate extends GdxTemplate {

    public Scene2dTemplate() {
        super("Project template included simple launchers and an `ApplicationAdapter` extension that draws a simple GUI stage on the screen.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils.readResourceFileAsString("generator/dynamic/templates/scene2d.txt");
    }
}
