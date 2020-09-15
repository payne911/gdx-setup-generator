package com.github.payne.generator;

import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.VirtualFileSystem;
import com.github.payne.logic.LogicProcessor;

@NotTested
public class Generator implements IGenerator {

    @Override
    public GeneratedProject generateFileStructure(GeneratorConfigs input) {
        // todo: validation on inputs

        GeneratedProject output = new GeneratedProject();
        output.setVirtualFileSystem(new VirtualFileSystem(input.getProjectName()));

        LogicProcessor logicProcessor = new LogicProcessor(output);
        logicProcessor.applyInputs(input);

        output.getVirtualFileSystem().sortByNames(); // enforcing a sorted output
        return output;
    }
}
