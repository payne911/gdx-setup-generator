package com.github.payne.generator;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;

public class Generator implements IGenerator {

    @Override
    public GeneratedProject generateFileStructure(GeneratorConfigs generatorConfigs) {
        GeneratedProject generatedProject = new GeneratedProject();
        // todo: implement the logic
        generatedProject.getVirtualFileSystem().sortByNames();
        return generatedProject;
    }
}
