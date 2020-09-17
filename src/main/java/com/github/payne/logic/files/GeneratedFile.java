package com.github.payne.logic.files;

import com.github.payne.generator.output.vfs.FileNode;
import lombok.Data;

@Data
public abstract class GeneratedFile {

    private final String fileName;

    public abstract String getContent();

    public FileNode createFile() {
        return new FileNode(fileName, getContent().getBytes());
    }
}
