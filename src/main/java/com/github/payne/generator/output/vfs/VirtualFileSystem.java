package com.github.payne.generator.output.vfs;

import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.exceptions.ResourceFileReadException;
import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * Simulates a File System. Acts as a custom Tree data structure where the files and folders are
 * {@link FileNode}.
 */
@Data
public class VirtualFileSystem implements AppendableTree {

    private final FileNode root;

    public VirtualFileSystem(String projectName) {
        this.root = new FileNode(projectName);
    }

    @Override
    public FileNode getRoot() {
        return root;
    }

    @Override
    public FileNode addToRoot(FileNode child) {
        return addToParent(root, child);
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
    public FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot) {
        byte[] content = FileUtils.readResourceFile(srcPathFromRes);

        String name = srcPathFromRes.get(srcPathFromRes.size() - 1);
        FileNode created = new FileNode(name, content);
        return addFromRoot(destPathFromRoot, created);
    }

    @Override
    public FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot,
            String rename) {
        FileNode copied = copyFile(srcPathFromRes, destPathFromRoot);
        copied.setName(rename);
        return copied;
    }

    /**
     * Copies the content of a folder recursively. May include or not the folder itself.
     * <p>
     * Folders specified in the {@code destPathFromRoot} parameter and which don't exist will be
     * created.
     * <p>
     * This implementation reads the input folder's content, and expects to find its children listed
     * separated by a new line ({@code "\n"}). Thus, recursively, to see if any of the children were
     * actually folders themselves too, their content is also looked into to test if the file names
     * exist as children. If a {@link NullPointerException} is thrown, then it's because it was not
     * a folder.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For "{@code your-project-name-as-root/folder}", use {@code
     *                         Arrays.asList("folder")}}.
     * @param include          {@code true} if you want to copy the designated folder in {@code
     *                         srcPathFromRes}, {@code false} if you want only the content.
     * @return {@code true} only if {@code srcPathFromRes} pointed to a valid folder.
     */
    @Override
    public boolean copyFolder(List<String> srcPathFromRes, List<String> destPathFromRoot,
            boolean include) {
        // todo: refactor this (currently hacky version) into something actually robust and portable
        if (include) {
            String folderName = srcPathFromRes.get(srcPathFromRes.size() - 1);
            addFromRoot(destPathFromRoot, new FileNode(folderName));
            destPathFromRoot = FileUtils.appendFilePath(destPathFromRoot, folderName);
        }
        String content = FileUtils.readResourceFileAsString(srcPathFromRes);
        String[] fileCandidates = content.split("\n"); // folder's content = filesName.join("\n")
        for (String fileName : fileCandidates) { // each "file" could actually be a folder
            try {
                List<String> appendedSrc = FileUtils.appendFilePath(srcPathFromRes, fileName);
                List<String> appendedDest = FileUtils.appendFilePath(destPathFromRoot, fileName);
                boolean isFolder = copyFolder(appendedSrc, appendedDest, false);
                if (!isFolder) {
                    copyFile(appendedSrc, destPathFromRoot);
                }
            } catch (ResourceFileReadException e) {
                /* NPE is "expected" so we're avoiding pollution of logs by not printing those. */
                if (!(e.getCause() instanceof NullPointerException)) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    @NotTested
    public FileNode copyFileToRoot(List<String> srcPathFromRes) {
        return copyFile(srcPathFromRes, Arrays.asList());
    }

    @Override
    @NotTested
    public FileNode copyFileToRoot(List<String> srcPathFromRes, String rename) {
        return copyFile(srcPathFromRes, Arrays.asList(), rename);
    }

    @Override
    public boolean copyFolderToRoot(List<String> srcPathFromRes, boolean include) {
        return copyFolder(srcPathFromRes, Arrays.asList(), include);
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
