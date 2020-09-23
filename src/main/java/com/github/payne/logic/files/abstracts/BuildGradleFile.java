package com.github.payne.logic.files.abstracts;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.utils.StringUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class BuildGradleFile extends DynamicFile {

    protected final Set<String> buildDeps = new HashSet<>();
    protected final Set<String> dependencies = new HashSet<>();

    public BuildGradleFile(final String resourcePath, final GeneratorConfigs input) {
        super("build.gradle", resourcePath, input);
    }

    public String joinDependencies(Collection<String> dependencies) {
        return joinDependencies(dependencies, "implementation", "\t");
    }

    public String joinDependencies(Collection<String> dependencies, String type) {
        return joinDependencies(dependencies, type, "\t");
    }

    public String joinDependencies(Collection<String> dependencies, String type, String preType) {
        if (dependencies.isEmpty()) {
            return "\n";
        } else {
            String prefix = preType + type + " ";
            String postfix = "\n";
            return StringUtils.join(dependencies, prefix, "\n" + prefix, postfix);
        }
    }

    public void addDependency(String dependency) {
        dependencies.add("\"" + dependency + "\"");
    }

    public void addBuildDependency(String dependency) {
        buildDeps.add("\"" + dependency + "\"");
    }
}
