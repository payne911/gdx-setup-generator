package com.github.payne.logic.files;

import lombok.Data;

/**
 * Represents a directory with source files.
 */
@Data
public class SourceDirectory extends SavableProjectFile {

    private String projectName;
}
