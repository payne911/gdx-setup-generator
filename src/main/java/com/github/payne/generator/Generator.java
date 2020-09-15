package com.github.payne.generator;

import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.VirtualFileSystem;
import com.github.payne.logic.LogicProcessor;

@NotTested
public class Generator implements IGenerator {

    @Override
    public GeneratedProject generateFileStructure(GeneratorConfigs input) {
        addDefaults(input);
        GeneratedProject output = init(input);
        validate(input);
        generate(input, output);
        groom(output);
        return output;
    }

    /**
     * Used to alter the input {@link GeneratorConfigs} to ensure certain default values are there.
     *
     * @param input the {@link GeneratorConfigs} which gets modified.
     */
    private void addDefaults(GeneratorConfigs input) {
        input.getPlatforms().add(Platform.CORE);
    }

    /**
     * @return {@code false} only if the {@link GeneratorConfigs} provided has something wrong.
     */
    private boolean validate(GeneratorConfigs input) {
        // todo: validation on inputs ?
        return true;
    }

    private GeneratedProject init(GeneratorConfigs input) {
        VirtualFileSystem vfs = new VirtualFileSystem(input.getProjectName());
        return new GeneratedProject(vfs);
    }

    private void generate(GeneratorConfigs input, GeneratedProject project) {
        LogicProcessor logicProcessor = new LogicProcessor(input, project);
        logicProcessor.applyInputs();
    }

    /**
     * Last operations to apply to the {@link GeneratedProject} after the input was used to generate
     * it, but right before it is returned.
     *
     * @param project the {@link GeneratedProject} which gets modified.
     */
    private void groom(GeneratedProject project) {
        project.getVirtualFileSystem().sortByNames(); // enforcing a sorted output
    }
}
