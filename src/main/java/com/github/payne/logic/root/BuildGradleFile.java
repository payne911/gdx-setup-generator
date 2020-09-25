package com.github.payne.logic.root;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.utils.StringUtils;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BuildGradleFile extends DynamicFile {

    protected final Set<String> buildDeps = new LinkedHashSet<>();
    protected final Set<String> dependencies = new LinkedHashSet<>();

    public BuildGradleFile(final String resourcePath, final GeneratorConfigs input) {
        super("build.gradle", resourcePath, input);
    }

    /**
     * Dependencies are added as 'implementation'.
     *
     * @return the entire String to be used as a replacement.
     */
    public String joinDependencies(Collection<String> dependencies) {
        return joinDependencies(dependencies, "implementation", "\t");
    }

    /**
     * @param type any prefix of dependency: {@code implementation}, {@code api}, etc.
     * @return the entire String to be used as a replacement.
     */
    public String joinDependencies(Collection<String> dependencies, String type) {
        return joinDependencies(dependencies, type, "\t");
    }

    /**
     * @param type    any prefix of dependency: {@code implementation}, {@code api}, etc.
     * @param preType used for formatting: usually tabs ("{@code \t}")
     * @return the entire String to be used as a replacement.
     */
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

    protected void addJvmLanguagesDependencies() {
        if (input.contains(Language.GROOVY)) {
            addDependency("org.codehaus.groovy:groovy-all:\\$groovyVersion");
        }
        if (input.contains(Language.SCALA)) {
            addDependency("org.scala-lang:scala-library:\\$scalaVersion");
        }
        if (input.contains(Language.KOTLIN)) {
            addDependency("org.jetbrains.kotlin:kotlin-stdlib:\\$kotlinVersion");
        }
    }

    protected void addSharedProjectDependency(String type) {
        final String replacement = input.contains(Platform.SHARED)
                ? "\t" + type + " project(':shared')"
                : "";
        assignKey("shared", replacement);
    }
}
