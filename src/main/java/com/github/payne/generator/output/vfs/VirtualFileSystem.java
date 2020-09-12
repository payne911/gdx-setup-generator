package com.github.payne.generator.output.vfs;

import com.github.payne.utils.FileUtils;
import java.util.List;
import lombok.Data;

/**
 * Simulates a File System. Acts as a custom Tree data structure where the files and folders are
 * {@link FileNode}.
 */
@Data
public class VirtualFileSystem implements SavableVfs {

    private final FileNode root;

    @Override
    public void addFromRoot(List<String> pathFromRoot, FileNode child) {
        addRelativeToParent(pathFromRoot, root, child);
    }

    @Override
    public void addToParent(FileNode parent, FileNode child) {
        parent.addChild(child);
    }

    @Override
    public void addRelativeToParent(List<String> pathFromParent, FileNode parent, FileNode child) {
        if (FileUtils.isEmptyPath(pathFromParent)) { // trivial case: empty query
            addToParent(parent, child);
            return;
        }

        FileNode newParent = parent.getOrCreateChild(pathFromParent.get(0));
        addRelativeToParent(pathFromParent.subList(1, pathFromParent.size()), newParent, child);
    }

    @Override
    public void copy(List<String> srcPathFromResources, List<String> destinationPathFromRoot) {
        // todo
    }

    @Override
    public void sortByNames() {
        // todo (BFS)
    }
}
