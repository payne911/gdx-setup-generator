package com.github.payne.logic.files;

import com.github.payne.utils.StringUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public abstract class BuildGradleFile extends GeneratedFile {

    @Getter
    protected final Set<String> buildDependencies = new HashSet<>();
    @Getter
    protected final Set<String> dependencies = new HashSet<>();

    public BuildGradleFile() {
        super("build.gradle");
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
            String prefix = preType + type;
            String postfix = "\n";
            return StringUtils.join(dependencies, prefix, "\n" + prefix, postfix);
        }
    }

    /**
     * @param dependency will be added as "implementation" dependency, quoted.
     */
    public void addDependency(String dependency) {
        dependencies.add("\"" + dependency + "\"");
    }
}
