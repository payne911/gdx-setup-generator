package com.github.payne.logic.folders.root;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.folders.DynamicFile;
import com.github.payne.utils.StringUtils;
import com.github.payne.utils.annotations.NotTested;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
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

    /**
     * Specifically designed for the treatment of dependencies coming from a JSON.
     *
     * @param jsonDependency something like {@code com.github.crykn:libgdx-screenmanager} or {@code
     *                       com.badlogicgames.gdx:gdx-box2d-platform:natives-armeabi}
     * @param libraryName    something like {@code my-library}
     * @return something like {@code com.github.crykn:libgdx-screenmanager:$mylibraryVersion}
     * @see StringUtils#keepAlphaNumerics(String)
     * @see GdxThirdParty
     */
    @NotTested
    protected String getDependencyString(String jsonDependency, String libraryName) {
        final String[] sections = jsonDependency.split(":");
        final String versionString = "\\$" + StringUtils.keepAlphaNumerics(libraryName) + "Version";
        String result;
        if (sections.length == 2) {
            result = jsonDependency + ":" + versionString;
        } else if (sections.length == 3) {
            String[] versionInTheMiddle = new String[4];
            versionInTheMiddle[0] = sections[0];
            versionInTheMiddle[1] = sections[1];
            versionInTheMiddle[2] = versionString;
            versionInTheMiddle[3] = sections[2];
            result = String.join(":", versionInTheMiddle);
        } else {
            result = "ERROR with " + jsonDependency;
        }
        return "\"" + result + "\"";
    }

    /**
     * Specifically designed for the treatment of dependencies coming from a JSON.
     *
     * @param dependenciesCollection the collection of dependencies that will have the string
     *                               appended
     * @param getModuleDeps          the extracted collection of dependencies coming from the json
     *                               for the appropriate module
     * @param key                    key to be replaced (for example: "{@code dependencies}")
     * @param depType                a dependency type, such as "{@code implementation}"
     * @see GdxThirdParty
     */
    @NotTested
    protected void addThirdPartiesToModule(Set<String> dependenciesCollection,
            Function<State, Collection<String>> getModuleDeps, String key,
            String depType) {
        input.getJsonLibraries().forEach(libraryJson -> {
            GdxThirdParty.State libraryDeps = libraryJson.getState(input.getLibGdxVersionObject());
            getModuleDeps.apply(libraryDeps).forEach(dep -> {
                String depString = getDependencyString(dep, libraryJson.getName());
                dependenciesCollection.add(depString);
            });
        });
        assignKey(key, joinDependencies(dependencies, depType));
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
