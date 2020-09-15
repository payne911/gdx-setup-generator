package com.github.payne.generator.output.vfs;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.annotations.NotTested;
import com.github.payne.utils.FileUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * Simulates a File System. Acts as a custom Tree data structure where the files and folders are
 * {@link FileNode}.
 */
@Data
public class VirtualFileSystem implements SavableVfs {

    private final FileNode root;

    public VirtualFileSystem(String projectName) {
        this.root = new FileNode(projectName);
    }

    @Override
    public FileNode getRoot() {
        return root;
    }

    @Override
    public FileNode addFromRoot(List<String> pathFromRoot, FileNode child) {
        return addRelativeToParent(pathFromRoot, root, child);
    }

    @Override
    public FileNode addToParent(FileNode parent, FileNode child) {
        return parent.addChild(child);
    }

    @Override
    public FileNode addRelativeToParent(List<String> pathFromParent, FileNode parent,
            FileNode child) {
        if (FileUtils.isEmptyPath(pathFromParent)) { // trivial case: empty query
            return addToParent(parent, child);
        }

        FileNode newParent = parent.getOrCreateChild(pathFromParent.get(0));
        return addRelativeToParent(pathFromParent.subList(1, pathFromParent.size()), newParent,
                child);
    }

    @Override
    @SneakyThrows // todo: don't forget to remove
    public FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot) {
        String srcPath = FileUtils.joinPath(srcPathFromRes);
        byte[] content = getClass().getClassLoader().getResourceAsStream(srcPath).readAllBytes();
        // todo: verify that "readAllBytes" is supported by GWT

        String name = srcPathFromRes.get(srcPathFromRes.size() - 1);
        FileNode created = new FileNode(name, content);
        return addFromRoot(destPathFromRoot, created);
    }

    @Override
    @SneakyThrows // todo: don't forget to remove
    @NotTested
    @NotImplemented
    public FileNode copyFolder(List<String> srcPathFromRes, List<String> destPathFromRoot) {
        // todo: content of folders is string of files and folders names, separated by "\n"...
        return copyFile(srcPathFromRes, destPathFromRoot);
    }

    @Override
    public void sortByNames() {
        bfsSort(root);
    }

    private void bfsSort(FileNode node) {
        Map<Boolean, List<FileNode>> split = node.getChildren().stream()
                .collect(Collectors.partitioningBy(FileNode::isFolder));
        List<FileNode> folders = split.get(true);
        List<FileNode> files = split.get(false);

        sortByName(folders);
        sortByName(files);

        List<FileNode> sortedList = new LinkedList<>();
        sortedList.addAll(folders); // folders first
        sortedList.addAll(files);
        node.setChildren(sortedList); // replacing older list

        folders.forEach(this::bfsSort); // recursively going through all sub-folders
    }

    private void sortByName(List<FileNode> nodes) {
        nodes.sort((node1, node2) -> {
            String name1 = node1.getName();
            String name2 = node2.getName();
            return name1.compareToIgnoreCase(name2);
        });
    }
}
