package com.github.payne.generator.output;

import com.github.payne.generator.output.vfs.AppendableTree;
import lombok.Data;

/**
 * The result of passing the {@link com.github.payne.generator.input.GeneratorConfigs} in the {@link
 * com.github.payne.generator.Generator}.
 */
@Data
public class GeneratedProject {

    /**
     * A representation of the generated project as some kind of virtual File System. A parent node
     * is always a folder.
     */
    private final AppendableTree virtualFileSystem;
    private String errorMessage = "";

    /**
     * If a project isn't valid, it means that something went wrong while trying to build the
     * project using the provided {@link com.github.payne.generator.input.GeneratorConfigs}.
     *
     * @return {@code false} only if an error occurred during the generation of the project.
     */
    public boolean isValid() {
        return errorMessage.isBlank();
    }
}
