package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;
import java.util.Arrays;

public class ClassicTemplate extends GdxTemplate {

    public ClassicTemplate() {
        super("Project template includes simple launchers and an `ApplicationAdapter` extension that draws BadLogic logo.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils.readResourceFileAsString("generator/dynamic/templates/classic.txt");
    }

    @Override
    public void addApplicationListener(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage) {
        super.addApplicationListener(input, vfs, corePackage);

        /* Add the logo */
        vfs.copyFile("generator/static/templates/classic/badlogic.png",
                Arrays.asList(input.getAssetsFolderName()));
    }
}
