package com.github.payne.generator;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.VirtualFileSystem;
import com.github.payne.logic.LogicProcessor;
import com.github.payne.utils.annotations.NotTested;

@NotTested
public class Generator implements IGenerator {

    @Override
    public GeneratedProject generateFileStructure(GeneratorConfigs input) {
        validate(input);
        addDefaults(input);
        GeneratedProject output = init(input);
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
        // todo: maybe move primitive defaults out of GenConfigs and add WarningMessage for each ?
        coreDefault(input);
        javaDefault(input);
    }

    private void coreDefault(GeneratorConfigs input) {
        input.getPlatforms().add(Platform.CORE); // it's a set so there can't be duplicates
    }

    private void javaDefault(GeneratorConfigs input) {
        Language java = Language.JAVA;
        if (input.contains(java)) {
            return;
        }
        VersionedLanguage defaultJava = new VersionedLanguage(java, java.getDefaultVersion());
        input.getLanguages().add(defaultJava);
    }

    /**
     * @return {@code false} only if the {@link GeneratorConfigs} provided has something wrong.
     */
    private boolean validate(GeneratorConfigs input) {
        // todo: validation on inputs ?

        /*
            Examples:
                * GWT version
                * Android Target API [9,30]
                * Java version [7,14] + LibGdxVersion utils conversion from input
                * GWT with other JVM languages
                * Android platform without AndroidSdk
                * VisUi library + AddOn.Skin selected?
                * etc
         */

        if (input.getTemplate().equals(Template.SCENE_2D)) {
            input.getAddOns().add(AddOn.GUI_ASSETS);
        }

        return true;
    }

    private GeneratedProject init(GeneratorConfigs input) {
        AppendableTree vfs = new VirtualFileSystem(input.getProjectName());
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
