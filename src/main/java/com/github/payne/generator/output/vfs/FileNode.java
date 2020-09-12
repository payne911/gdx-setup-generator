package com.github.payne.generator.output.vfs;

import com.github.payne.utils.FileUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

/**
 * The representation of a File or a Folder in the {@link VirtualFileSystem}.
 */
@Data
public class FileNode { // todo: maybe 'abstract' and create 'File' and 'Folder' ?

    private final String name;
    private final boolean isFolder; // todo: could be a utility method...

    @ToString.Exclude
    private byte[] content;

    private FileNode parent; // todo: maybe prevent the Setter from being generated ?

    @ToString.Exclude // to prevent StackOverflow
    @Setter(AccessLevel.NONE)
    private final List<FileNode> children = new LinkedList<>();

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

    /**
     * todo: overload with a File Separator (for OS-specific queries)
     *
     * @return something like {@code root/folder/file.txt}
     */
    public String getFullPath() {
        return getFullPath(this);
    }

    /**
     * Recursively joins the name of the Nodes all the way to the root folder.
     *
     * @param current node currently being observed
     * @return
     */
    private String getFullPath(FileNode current) {
        return current.isRoot()
                ? current.name
                : FileUtils.joinPath(getFullPath(current.parent), current.name);
    }

    private boolean isRoot() {
        return parent == null;
    }

    /**
     * Also takes care of setting the child's parent. If a child with the same name already exists,
     * its content is replaced with this new one's.
     *
     * @param child added to the list of children.
     * @return the newly created child Node
     */
    public FileNode addChild(FileNode child) {
        try {
            FileNode alreadyExistingChild = getChild(child.name);
            alreadyExistingChild.content = child.content;
            return alreadyExistingChild;
        } catch (NoSuchElementException e) {
            children.add(child);
            child.setParent(this);
            return child;
        }
    }

    /**
     * If the child with the given name doesn't exist, it is created as a directory.
     *
     * @param name name of the child Node
     * @return the found or created Node
     */
    public FileNode getOrCreateChild(String name) {
        return children.stream()
                .filter(node -> node.getName().equals(name))
                .findFirst()
                .orElseGet(() -> addChild(new FileNode(name)));
    }

    /**
     * Convenient method to avoid having to use the {@link Optional} class.
     *
     * @throws NoSuchElementException if a child with that name didn't exit
     */
    public FileNode getChild(String name) throws NoSuchElementException {
        return getOptionalChild(name).orElseThrow();
    }

    public Optional<FileNode> getOptionalChild(String name) {
        return children.stream()
                .filter(node -> node.getName().equals(name))
                .findFirst();
    }
}
