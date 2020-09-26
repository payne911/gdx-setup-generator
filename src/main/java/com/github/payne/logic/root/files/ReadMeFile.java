package com.github.payne.logic.root.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.DynamicFile;
import com.github.payne.utils.EnumMapper;
import com.github.payne.utils.FileUtils;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReadMeFile extends DynamicFile {

    SortedMap<String, String> gradleTasks = new TreeMap<>();

    public ReadMeFile(final GeneratorConfigs input) {
        super("README.md", "generator/dynamic/README.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        assignKey("readmeDescription",
                EnumMapper.getTemplate(input.getTemplate()).getReadmeDescription());
        assignKey("addGradleWrapper", getGradleWrapperString(input));
        assignKey("gradleTaskDescriptions", getGradleTasksString());
    }

    private String getGradleWrapperString(GeneratorConfigs input) {
        return input.contains(AddOn.GRADLE_WRAPPER)
                ? "Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands."
                : "Gradle wrapper was not included by default, so you have to install Gradle locally.";
    }

    private String getGradleTasksString() {
        permanentTasks();
        platformSpecificTasks();

        return gradleTasks.entrySet().stream()
                .map(entry -> "* `" + entry.getKey() + "`: " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }

    private void permanentTasks() {
        addTask("--daemon", "thanks to this flag, Gradle daemon will be used to run chosen tasks.");
        addTask("--offline", "when using this flag, cached dependency archives will be used.");
        addTask("--continue", "when using this flag, errors will not stop the tasks from running.");
        addTask("--refresh-dependencies",
                "this flag forces validation of all dependencies. Useful for snapshot versions.");
        addTask("idea", "generates IntelliJ project data.");
        addTask("cleanIdea", "removes IntelliJ project data.");
        addTask("eclipse", "generates Eclipse project data.");
        addTask("cleanEclipse", "removes Eclipse project data.");
        addTask("clean",
                "removes `build` folders, which store compiled classes and built archives.");
        addTask("test", "runs unit tests (if any).");
        addTask("build", "builds sources and archives of every project.");
    }

    private void platformSpecificTasks() {
        conditionalTask(Platform.SERVER, "run", "runs the ${name} application.");
        conditionalTask(Platform.HEADLESS, "run",
                "starts the ${name} application. Note: if ${name} sources were not modified - and the application still creates `ApplicationListener` from `core` project - this task might fail due to no graphics support.");
        conditionalTask(Platform.ANDROID, "lint", "performs Android project validation.");
        conditionalTask(Platform.DESKTOP_LEGACY, "run", "starts the application.");
        conditionalTask(Platform.DESKTOP_LEGACY, "jar",
                "builds application's runnable jar, which can be found at `${name}/build/libs`.");
        conditionalTask(Platform.LWJGL_3, "run", "starts the application.");
        conditionalTask(Platform.LWJGL_3, "jar",
                "builds application's runnable jar, which can be found at `${name}/build/libs`.");
        conditionalTask(Platform.HTML, "superDev",
                "compiles GWT sources and runs the application in SuperDev mode. It will be available at [localhost:8080/${name}](http://localhost:8080/${name}). Use only during development.");
        conditionalTask(Platform.HTML, "dist",
                "compiles GWT sources. The compiled application can be found at `${name}/build/dist`: you can use any HTTP server to deploy it.");
    }

    private void addTask(String command, String description) {
        gradleTasks.put(command, description);
    }

    /**
     * Only adds the specified task if the Platform is in the input configuration of the user.
     * <p>
     * If the description contains "{@code ${name}}", that will be replaced by the name of the
     * platform.
     * <p>
     * The designated command will have the platform's name prepended.
     *
     * @param platform    the platform required to add the task
     * @param command     the gradle task command
     * @param description description which may or not contain an {@code name} key for replacement
     */
    private void conditionalTask(Platform platform, String command, String description) {
        if (!input.contains(platform)) {
            return;
        }

        final String PLATFORM_NAME = platform.getValue();
        description = FileUtils.replaceStringContent(description, "name", PLATFORM_NAME);
        addTask(PLATFORM_NAME + ":" + command, description);
    }
}
