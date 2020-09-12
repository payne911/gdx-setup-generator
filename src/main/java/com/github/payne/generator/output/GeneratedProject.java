package com.github.payne.generator.output;

import com.github.payne.generator.output.vfs.VirtualFileSystem;
import lombok.Data;

/**
 * The result of passing the {@link com.github.payne.generator.input.GeneratorConfigs} in the {@link
 * com.github.payne.generator.Generator}.
 * <p>
 * todo: implement Visitor pattern for the users to be able to easily do stuff with the VFS
 */
@Data
public class GeneratedProject {

    /**
     * A representation of the generated project as some kind of virtual File System. A parent node
     * is always a folder.
     */
    private VirtualFileSystem virtualFileSystem;
    private String errorMessage;
}
