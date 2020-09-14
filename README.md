![build](https://github.com/payne911/gdx-setup-generator/workflows/Java%20CI%20with%20Gradle/badge.svg)

Input is ``GeneratorConfigs.java``.

Ouput is ``GeneratedProject.java``.

The ``VirtualFileSystem.java`` class is a custom tree (data structure) implementation. The root is obtained via ``#getRoot()``. All nodes of the tree are ``FileNode.java``, and navigation happens through those (via ``#getChildren()``, which returns a sorted ``LinkedList<FileNode>``).

![VisualizerSketch](media/visualizer_sketch.png)
