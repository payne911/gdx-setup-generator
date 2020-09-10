package com.github.payne.generator.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class BasicData {

    @NonNull
    private final String projectName;
    @NonNull
    private final String corePackage;
    @NonNull
    private final String mainClass;

    private final String androidSdkPath;
}
