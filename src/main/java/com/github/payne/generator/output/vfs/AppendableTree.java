package com.github.payne.generator.output.vfs;

import java.util.List;

/**
 * Basic CREATE operations for a Virtual File System. For the methods requiring a path as a {@code
 * List<String>}, it's recommended to use {@code Arrays.asList("my","path")} for conciseness.
 * <p>
 * todo: support "../" in path strings ?
 */
public interface AppendableTree {

    FileNode getRoot();

    /**
     * @return the newly-created node.
     */
    FileNode addToRoot(FileNode child);

    /**
     * @return the newly-created node.
     */
    FileNode addFromRoot(List<String> pathFromRoot, FileNode child);

    /**
     * @return the newly-created node.
     */
    FileNode addToParent(FileNode parent, FileNode child);

    /**
     * @return the newly-created node.
     */
    FileNode addRelativeToParent(List<String> pathFromParent, FileNode parent, FileNode child);

    /**
     * Create a new {@link FileNode} with the content of the File located in the <b>resources</b>
     * folder at the specified path.
     * <p>
     * If the desired file is at "{@code src/main/resources/some-folder/my-file.txt}", the input
     * destination path would be {@code Arrays.asList("some-folder")}, and the resulting {@link
     * FileNode} will have the exact same name and content as the original ("{@code my-file.txt}").
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For "{@code your-project-name-as-root/folder}", use {@code
     *                         Arrays.asList("folder")}}.
     * @return the newly-created node.
     */
    FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot);

    /**
     * Does the same as {@link #copyFile(List, List)}, but also renames the file after creating it.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For "{@code your-project-name-as-root/folder}", use {@code
     *                         Arrays.asList("folder")}}.
     * @param rename           The new name to be used for that copied file.
     * @return the newly-created node.
     */
    FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot, String rename);

    /**
     * Copies the content of a folder recursively. May include or not the folder itself.
     * <p>
     * Folders specified in the {@code destPathFromRoot} parameter and which don't exist will be
     * created.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For "{@code your-project-name-as-root/folder}", use {@code
     *                         Arrays.asList("folder")}}.
     * @param include          {@code true} if you want to copy the designated folder in {@code
     *                         srcPathFromRes}, {@code false} if you want only the content.
     * @return {@code true} only if {@code srcPathFromRes} pointed to a valid folder.
     */
    boolean copyFolder(List<String> srcPathFromRes, List<String> destPathFromRoot,
            boolean include);

    /**
     * Calls {@link #copyFile(List, List)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       Arrays.asList("some-folder")}.
     * @return the newly-created node.
     */
    FileNode copyFileToRoot(List<String> srcPathFromRes);

    /**
     * Calls {@link #copyFile(List, List, String)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       Arrays.asList("some-folder")}.
     * @param rename         The new name to be used for that copied file.
     * @return the newly-created node.
     */
    FileNode copyFileToRoot(List<String> srcPathFromRes, String rename);

    /**
     * Calls {@link #copyFolder(List, List, boolean)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       Arrays.asList("some-folder")}.
     * @param include        {@code true} if you want to copy the designated folder in {@code
     *                       srcPathFromRes}, {@code false} if you want only the content.
     * @return {@code true} only if {@code srcPathFromRes} pointed to a valid folder.
     */
    boolean copyFolderToRoot(List<String> srcPathFromRes, boolean include);

    /**
     * Places folders and then files in alphabetical order recursively through each folder, starting
     * from the root.
     * <p>
     * This is not case-sensitive: no particular order is enforced between "ab" and "AB".
     */
    void sortByNames();
}
