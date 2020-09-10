package com.github.payne.generator.output;

import lombok.Data;
import space.earlygrey.simplegraphs.DirectedGraph;
import space.earlygrey.simplegraphs.Graph;

@Data
public class GeneratedProject {

    /**
     * A representation of the generated project as some kind of virtual File System. A parent node
     * is always a folder. A leaf is always a file.
     */
    private Graph<FileNode> graph = new DirectedGraph<>();
    private String errorMessage;
}
