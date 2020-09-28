package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.templates.base.GdxTemplate;
import com.github.payne.utils.FileUtils;

public class GameTemplate extends GdxTemplate {

    public GameTemplate() {
        super("Project template includes simple launchers and a `Game` extension that sets the first screen.");
    }

    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return FileUtils.readResourceFileAsString("generator/dynamic/templates/game.txt");
    }

    @Override
    public void addApplicationListener(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage) {
        super.addApplicationListener(input, vfs, corePackage);

        /* Create FirstScreen */
        String firstScreenContent = FileUtils
                .readResourceFileAsString("generator/dynamic/templates/game-first-screen.txt");
        firstScreenContent = FileUtils.injectConfigs(firstScreenContent, input);
        FileNode firstScreen = new FileNode("FirstScreen.java", firstScreenContent.getBytes());
        corePackage.addChild(firstScreen);
    }
}
