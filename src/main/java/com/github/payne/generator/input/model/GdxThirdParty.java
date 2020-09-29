package com.github.payne.generator.input.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class GdxThirdParty {

    private final int formatVersion;

    private final String name;
    private final List<String> authors = new ArrayList<>();
    private final String description;
    private final String projectUrl;

    // todo: consider mapping to `Collection<LibGdxVersion>` to reduce duplication
    private final Map<LibGdxVersion, State> states = new HashMap<>();

    @Data
    public static class State {

        private final String libraryVersion;

        private final ArrayList<String> incompatiblePlatforms = new ArrayList<>();

        private final ArrayList<String> coreDependencies = new ArrayList<>();
        private final ArrayList<String> desktopDependencies = new ArrayList<>();
        private final ArrayList<String> gwtDependencies = new ArrayList<>();
        private final ArrayList<String> androidDependencies = new ArrayList<>();
        private final ArrayList<String> iOSDependencies = new ArrayList<>();

        private final ArrayList<String> gwtInherits = new ArrayList<>();

        private final ArrayList<String> androidPermissions = new ArrayList<>();
        private final ArrayList<String> androidNativeDependencies = new ArrayList<>();
    }

    public State getState(LibGdxVersion version) {
        // todo: what about missing libgdx versions?
        return getStates().getOrDefault(version, new State(version.toString()));
    }
}
