package com.github.payne.logic.files;

import com.github.payne.generator.output.FileNode;
import space.earlygrey.simplegraphs.Graph;

/**
 * Represents a directory with source files.
 *
 * @author MJ
 */
public class SourceDirectory extends SaveableProjectFile {

    @Override
    boolean save(Graph<FileNode> virtualFileSystem) {
        // todo: implement
        return false;
    }
}
