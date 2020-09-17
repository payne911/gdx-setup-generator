![build](https://github.com/payne911/gdx-setup-generator/workflows/build/badge.svg)

Input is ``GeneratorConfigs.java``.

Ouput is ``GeneratedProject.java``.

The ``VirtualFileSystem.java`` class is a custom tree (data structure) implementation. The root is obtained via ``#getRoot()``. All nodes of the tree are ``FileNode.java``, and navigation happens through those (via ``#getChildren()``, which returns a sorted ``LinkedList<FileNode>``).

![VisualizerSketch](media/visualizer_sketch.png)


# GWT

For GWT-compatibility, this needs to be changed:

* Downgrade JDK from 14 to 11 (text-blocks, etc.)
* ``FileUtils#readResourceFile`` uses a ClassLoader
* ``FileUtils#replaceResourceFileContent`` uses a Regex
* ``StringUtils#keepLetters`` uses a Regex

# Concerns

The replacement algorithm is vulnerable. It needs to check if the input text doesn't contain a key.