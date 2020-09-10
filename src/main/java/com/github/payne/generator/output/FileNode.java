package com.github.payne.generator.output;

import com.github.payne.utils.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A representation of a File. The {@link #idHash} is used to distinguish between files. The content
 * being potentially modified after inserting "the node into the tree" (i.e. "the file into the
 * virtual file system"), it can't be used for the equality test.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileNode {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private final int idHash = UUID.getUniqueId();

    private String name;
    private boolean isFolder;
    private byte[] content;

    /**
     * Creates a folder.
     *
     * @param name name of the folder.
     */
    public FileNode(String name) {
        this.name = name;
        this.isFolder = true;
    }

    /**
     * Creates a file.
     *
     * @param name    name of the file (including extension).
     * @param content content of the file.
     */
    public FileNode(String name, byte[] content) {
        this.name = name;
        this.content = content;
        this.isFolder = false;
    }
}
