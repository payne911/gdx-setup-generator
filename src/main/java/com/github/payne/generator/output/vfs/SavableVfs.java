package com.github.payne.generator.output.vfs;

import java.util.List;

/**
 * Basic CREATE operations for a Virtual File System. For the methods requiring a path as a {@code
 * List<String>}, it's recommended to use {@code Arrays.asList("my","path")} for conciseness.
 * <p>
 * todo: support "../" in path strings ?
 */
public interface SavableVfs {

    FileNode addFromRoot(List<String> pathFromRoot, FileNode child);

    FileNode addToParent(FileNode parent, FileNode child);

    FileNode addRelativeToParent(List<String> pathFromParent, FileNode parent, FileNode child);

    /**
     * Created a new {@link FileNode} with the content of the File located in the resources folder
     * at the specified path.
     * <p>
     * If the desired file is at {@code src/main/resources/some-folder/my-file.txt}, the input
     * destination path would be {@code Arrays.asList("some-folder")}, and the resulting {@link
     * FileNode} will have the exact same name and content as the original.
     *
     * @param srcPathFromRes   For {@code src/main/resources/some-folder/my-file.txt}, use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For {@code you-project-name-as-root/folder}, use {@code
     *                         Arrays.asList("folder")}}.
     * @return the newly-created node
     */
    FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot);

    FileNode copyFolder(List<String> srcPathFromRes, List<String> destPathFromRoot);

    /**
     * Places folders and then files in alphabetical order recursively through each folder, starting
     * from the root.
     * <p>
     * This is not case-sensitive: no particular order is enforced between "ab" and "AB".
     */
    void sortByNames();
}
