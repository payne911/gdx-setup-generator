package com.github.payne.generator;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class GeneratedProject {

    private Map<String, byte[]> virtualFileSystem = new HashMap<>();
    private String errorMessage;
}
