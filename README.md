Input is ``GeneratorConfigs.java``.

Ouput is ``GeneratedProject.java``.

The ``VirtualFileSystem.java`` class is a custom tree (data structure) implementation. The root is obtained via the ``#getRoot()``. All nodes of the tree are ``FileNode.java``, and navigation happens through those (via ``#getChildren()``, which returns a sorted ``LinkedList``).

![VisualizerSketch](media/visualizer_sketch.png)