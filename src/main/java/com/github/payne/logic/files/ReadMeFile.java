package com.github.payne.logic.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.logic.files.abstracts.DynamicFile;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReadMeFile extends DynamicFile {

    public ReadMeFile(final GeneratorConfigs input) {
        super("README.md", "generator/dynamic/README.txt", input);
    }

    @Override
    protected void assignKeys() {
        assignKey("projectName", input.getProjectName());
        assignKey("readmeDescription", ""); // todo: comes from Template!
        assignKey("addGradleWrapper", getGradleWrapperString(input));
        assignKey("gradleTaskDescriptions", getGradleTasksString());
    }

    private String getGradleWrapperString(GeneratorConfigs input) {
        return input.contains(AddOn.GRADLE_WRAPPER)
                ? "Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands."
                : "Gradle wrapper was not included by default, so you have to install Gradle locally.";
    }

    private String getGradleTasksString() {
        SortedMap<String, String> gradleTasks = new TreeMap<>();
        gradleTasks.put("idea", "generates IntelliJ project data.");
        gradleTasks.put("cleanIdea", "removes IntelliJ project data.");
        gradleTasks.put("eclipse", "generates Eclipse project data.");
        gradleTasks.put("cleanEclipse", "removes Eclipse project data.");
        gradleTasks.put("clean",
                "removes `build` folders, which store compiled classes and built archives.");
        gradleTasks.put("test", "runs unit tests (if any).");
        gradleTasks.put("build", "builds sources and archives of every project.");
        gradleTasks.put("--daemon",
                "thanks to this flag, Gradle daemon will be used to run chosen tasks.");
        gradleTasks
                .put("--offline", "when using this flag, cached dependency archives will be used.");
        gradleTasks.put("--continue",
                "when using this flag, errors will not stop the tasks from running.");
        gradleTasks.put("--refresh-dependencies",
                "this flag forces validation of all dependencies. Useful for snapshot versions.");

        // todo: finish this list ("addGradleTaskDescription" in liftoff)

        return gradleTasks.entrySet().stream()
                .map(entry -> "* `" + entry.getKey() + "`: " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
