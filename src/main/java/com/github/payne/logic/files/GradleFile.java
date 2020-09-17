package com.github.payne.logic.files;

import com.github.payne.utils.StringUtil;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public abstract class GradleFile {

    @Getter
    protected final Set<String> buildDependencies = new HashSet<>();
    @Getter
    protected final Set<String> dependencies = new HashSet<>();

    public static final String NAME = "build.gradle";

    public abstract String getContent();

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
            return StringUtil.join(dependencies, prefix, "\n" + prefix, postfix);
        }
    }

    /**
     * @param dependency will be added as "implementation" dependency, quoted.
     */
    public void addDependency(String dependency) {
        dependencies.add("\"" + dependency + "\"");
    }
}
