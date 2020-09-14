package com.github.payne.generator;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.VirtualFileSystem;

public class Generator implements IGenerator {

    @Override
    public GeneratedProject generateFileStructure(GeneratorConfigs input) {
        // todo: validation on inputs

        GeneratedProject output = new GeneratedProject();
        output.setVirtualFileSystem(new VirtualFileSystem(input.getProjectName()));

        // todo: implement the logic

        output.getVirtualFileSystem().sortByNames(); // enforcing a sorted output
        return output;
    }
}
