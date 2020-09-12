package com.github.payne.generator.output.vfs;

import java.util.List;

/**
 * Basic CREATE operations for a Virtual File System. For the methods requiring a path as a {@code
 * List<String>}, it's recommended to use {@code Arrays.asList("my","path")} for conciseness.
 * <p>
 * todo: support "../" in path strings ?
 */
public interface SavableVfs {

    void addFromRoot(List<String> pathFromRoot, FileNode child);

    void addToParent(FileNode parent, FileNode child);

    void addRelativeToParent(List<String> pathFromParent, FileNode parent, FileNode child);

    void copy(List<String> srcPathFromResources, List<String> destinationPathFromRoot);

    void sortByNames();
}
