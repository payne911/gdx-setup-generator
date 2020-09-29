package com.github.payne.generator.output.vfs;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Basic CREATE operations for a Virtual File System. For the methods requiring a path as a {@code
 * List<String>}, it's recommended to use {@code Arrays.asList("my","path")} for conciseness.
 * <p>
 * Only the {@code resources} paths are allowed to be specified as Strings to make it easier to
 * recognize which paths are navigable (your IDE should allow you to "{@code Ctrl+Click}" to open
 * that referenced file).
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
     * Create a new {@link FileNode} with the content of the file located in the {@code resources}
     * folder at the specified path.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder", "my-file.txt")}.
     * @param destPathFromRoot For "{@code project-root/folder}", use {@code Arrays.asList("folder")}}.
     * @return the newly-created node.
     */
    FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot);

    /**
     * Create a new {@link FileNode} with the content of the file located in the {@code resources}
     * folder at the specified path.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         "some-folder/my-file.txt"}.
     * @param destPathFromRoot For "{@code project-root/folder}", use {@code Arrays.asList("folder")}}.
     * @return the newly-created node.
     */
    FileNode copyFile(String srcPathFromRes, List<String> destPathFromRoot);

    /**
     * Does the same as {@link #copyFile(List, List)}, but also renames the file after creating it.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder", "my-file.txt")}.
     * @param destPathFromRoot For "{@code project-root/folder}", use {@code Arrays.asList("folder")}}.
     * @param rename           The new name to be used for that copied file.
     * @return the newly-created node.
     */
    FileNode copyFile(List<String> srcPathFromRes, List<String> destPathFromRoot, String rename);

    /**
     * Does the same as {@link #copyFile(String, List)}, but also renames the file after creating
     * it.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         "some-folder/my-file.txt"}.
     * @param destPathFromRoot For "{@code project-root/folder}", use {@code Arrays.asList("folder")}}.
     * @param rename           The new name to be used for that copied file.
     * @return the newly-created node.
     */
    FileNode copyFile(String srcPathFromRes, List<String> destPathFromRoot, String rename);

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
    boolean copyFolder(List<String> srcPathFromRes, List<String> destPathFromRoot, boolean include);

    /**
     * Copies the content of a folder recursively. May include or not the folder itself.
     * <p>
     * Folders specified in the {@code destPathFromRoot} parameter and which don't exist will be
     * created.
     *
     * @param srcPathFromRes   For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                         Arrays.asList("some-folder")}.
     * @param destPathFromRoot For "{@code project-root/folder}", use {@code Arrays.asList("folder")}}.
     * @param include          {@code true} if you want to copy the designated folder in {@code
     *                         srcPathFromRes}, {@code false} if you want only the content.
     * @return {@code true} only if {@code srcPathFromRes} pointed to a valid folder.
     */
    boolean copyFolder(String srcPathFromRes, List<String> destPathFromRoot, boolean include);

    /**
     * Calls {@link #copyFile(List, List)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       Arrays.asList("some-folder")}.
     * @return the newly-created node.
     */
    FileNode copyFileToRoot(List<String> srcPathFromRes);

    /**
     * Calls {@link #copyFile(List, List)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       "some-folder/my-file.txt"}.
     * @return the newly-created node.
     */
    FileNode copyFileToRoot(String srcPathFromRes);

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
     * Calls {@link #copyFile(List, List, String)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       "some-folder/my-file.txt"}.
     * @param rename         The new name to be used for that copied file.
     * @return the newly-created node.
     */
    FileNode copyFileToRoot(String srcPathFromRes, String rename);

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
     * Calls {@link #copyFolder(List, List, boolean)} with the destination path being the root.
     *
     * @param srcPathFromRes For "{@code src/main/resources/some-folder/my-file.txt}", use {@code
     *                       Arrays.asList("some-folder")}.
     * @param include        {@code true} if you want to copy the designated folder in {@code
     *                       srcPathFromRes}, {@code false} if you want only the content.
     * @return {@code true} only if {@code srcPathFromRes} pointed to a valid folder.
     */
    boolean copyFolderToRoot(String srcPathFromRes, boolean include);

    /**
     * Traverses the whole tree structure in a depth-first-search way.
     * <p>
     * The {@code BiConsumer} will be applied on nodes as it traverses. <b>The first argument is the
     * node currently being traversed, and the second argument is the depth of that node</b>.
     * <p>
     * The traversal starts at the root (the project's folder), with a depth of 0. Thus, for
     * example, the {@code assets} folder would have a depth of 1 in the Consumer.
     *
     * @param consumer an action to be applied on every node of the tree.
     */
    void depthFirstTraversal(BiConsumer<FileNode, Integer> consumer);

    /**
     * Places folders and then files in alphabetical order recursively through each folder, starting
     * from the root.
     * <p>
     * This is not case-sensitive: no particular order is enforced between "ab" and "AB".
     */
    void sortByNames();
}
