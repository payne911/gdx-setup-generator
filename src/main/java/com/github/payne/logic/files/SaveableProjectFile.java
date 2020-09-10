package com.github.payne.logic.files;

import com.github.payne.generator.output.FileNode;
import space.earlygrey.simplegraphs.Graph;

/**
 * Common interface of files generated or copied during project creation.
 *
 * @author MJ
 */
public abstract class SaveableProjectFile {

    /**
     * Relative path to the project file.
     */
    protected String path;

    /**
     * @param virtualFileSystem
     * @return {@code true} only if another file with the same name already existed at the desired
     * location.
     */
    abstract boolean save(Graph<FileNode> virtualFileSystem);
}
