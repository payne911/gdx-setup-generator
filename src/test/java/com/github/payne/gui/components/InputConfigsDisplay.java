package com.github.payne.gui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.generator.input.model.LibGdxVersion;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class InputConfigsDisplay {

    private final Skin skin;
    private final Table table;
    private final Button generateBtn;
    private final ScrollPane scrollPane;

    /* Main settings */
    private TextField libGdxVersion;
    private TextField projectName;
    private TextField corePackage;
    private TextField mainClass;
    private TextField assetsFolderName;
    private TextField javaVersion;

    /* Platforms */
    private CheckBox corePlatform;
    private CheckBox androidPlatform;
    private CheckBox desktopPlatform;
    private CheckBox headlessPlatform;
    private CheckBox htmlPlatform;
    private CheckBox iosPlatform;
    private CheckBox lwjgl3Platform;
    private CheckBox serverPlatform;
    private CheckBox sharedPlatform;

    /* Templates */
    private SelectBox<String> templatesList;

    /* AddOns */
    private CheckBox readMeAddon;
    private CheckBox guiAssetsAddon;
    private CheckBox gradleWrapperAddon;

    /* JVM Languages */
    private CheckBox javaLanguage;
    private CheckBox kotlinLanguage;
    private CheckBox scalaLanguage;
    private CheckBox groovyLanguage;

    /* Advanced settings */
    private TextField applicationVersion;
    private TextField postGenerationTask;
    private TextField serverJavaVersion;
    private TextField desktopJavaVersion;
    private TextField gwtPluginVersion;
    private TextField roboVmVersion;
    private TextField androidSdkPath;
    private TextField androidPluginVersion;
    private TextField targetAndroidApi;
    private TextField fakeJsonDependencies;

    public InputConfigsDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);
        scrollPane = new ScrollPayne(table, skin);
        scrollPane.setOverscroll(false, false);
        generateBtn = new TextButton("GO", skin);

        configureTable();
        setUp();
    }

    private void configureTable() {
        table.clearChildren();
        table.align(Align.topLeft);
        table.defaults().align(Align.left).pad(4);
    }

    private void setUp() {
        var defaults = new GeneratorConfigs(); // dummy to get default values

        title("MAIN SETTINGS");
        libGdxVersion = textField("libGdxVersion", defaults.getLibGdxVersion()).getActor();
        projectName = textField("projectName", defaults.getProjectName()).getActor();
        table.row();
        corePackage = textField("corePackage", defaults.getCorePackage()).getActor();
        mainClass = textField("mainClass", defaults.getMainClass()).getActor();
        table.row();
        assetsFolderName = textField("assetsFolderName", defaults.getAssetsFolderName()).getActor();
        javaVersion = textField("javaVersion", defaults.getJavaVersion()).getActor();

        table.row();
        title("MODULES");
        Table platforms = new Table(skin);
        platforms.defaults().padLeft(30).padRight(30).align(Align.left);
        label(platforms, "Select a platform: ");
        androidPlatform = checkBox(platforms, Platform.ANDROID.getString()).getActor();
        desktopPlatform = checkBox(platforms, Platform.DESKTOP_LEGACY.getString()).getActor();
        lwjgl3Platform = checkBox(platforms, Platform.LWJGL_3.getString(), true).getActor();
        htmlPlatform = checkBox(platforms, Platform.HTML.getString()).getActor();
        headlessPlatform = checkBox(platforms, Platform.HEADLESS.getString()).getActor();
        iosPlatform = checkBox(platforms, Platform.IOS.getString()).getActor();
        platforms.row();
        label(platforms, "Extra modules: ");
        corePlatform = checkBox(platforms, Platform.CORE.getString(), true, true).getActor();
        serverPlatform = checkBox(platforms, Platform.SERVER.getString()).getActor();
        sharedPlatform = checkBox(platforms, Platform.SHARED.getString()).colspan(4).getActor();
        table.add(platforms).colspan(4);

        table.row();
        title("TEMPLATE");
        Table templates = new Table(skin);
        templates.defaults().padLeft(30).padRight(30);
        label(templates, "Select a template: ");
        templatesList = selectBox(templates, 2,
                Template.APPLICATION_ADAPTER.getString(),
                Template.APPLICATION_LISTENER.getString(),
                Template.CLASSIC.getString(),
                Template.GAME.getString(),
                Template.INPUT_PROCESSOR.getString(),
                Template.SCENE_2D.getString())
                .getActor();
        table.add(templates).colspan(4);

        table.row();
        title("ADD ONS");
        Table addOns = new Table(skin);
        addOns.defaults().padLeft(30).padRight(30);
        guiAssetsAddon = checkBox(addOns, AddOn.GUI_ASSETS.getString(), true).getActor();
        readMeAddon = checkBox(addOns, AddOn.README.getString(), true).getActor();
        gradleWrapperAddon = checkBox(addOns, AddOn.GRADLE_WRAPPER.getString()).getActor();
        table.add(addOns).colspan(4);

        table.row();
        title("JVM LANGUAGES");
        Table jvmLanguages = new Table(skin);
        jvmLanguages.defaults().padLeft(30).padRight(30);
        javaLanguage = checkBox(jvmLanguages, Language.JAVA.getString(), true, true).getActor();
        kotlinLanguage = checkBox(jvmLanguages, Language.KOTLIN.getString()).getActor();
        scalaLanguage = checkBox(jvmLanguages, Language.SCALA.getString()).getActor();
        groovyLanguage = checkBox(jvmLanguages, Language.GROOVY.getString()).getActor();
        table.add(jvmLanguages).colspan(4);

        table.row();
        title("ADVANCED SETTINGS");
        applicationVersion = textField("applicationVersion", defaults.getApplicationVersion())
                .getActor();
        postGenerationTask = textField("postGenerationTask", defaults.getPostGenerationTask())
                .getActor();
        table.row();
        serverJavaVersion = textField("serverJavaVersion", defaults.getServerJavaVersion())
                .getActor();
        desktopJavaVersion = textField("desktopJavaVersion", defaults.getDesktopJavaVersion())
                .getActor();
        table.row();
        gwtPluginVersion = textField("gwtPluginVersion", defaults.getGwtPluginVersion()).getActor();
        roboVmVersion = textField("roboVmVersion", defaults.getRoboVmVersion()).getActor();
        table.row();
        androidSdkPath = textField("androidSdkPath", "C:/something/somewhere/sdk").getActor();
        androidPluginVersion = textField("androidPluginVersion", defaults.getAndroidPluginVersion())
                .getActor();
        table.row();
        targetAndroidApi = textField("targetAndroidApi", defaults.getTargetAndroidApi().toString())
                .getActor();
        fakeJsonDependencies = textField("fakeJsonDependencies", "1").getActor();
    }

    private Cell<TextField> textField(String name, String defaults) {
        label(name + ":").align(Align.left);
        var injected = new TextField(defaults, skin);
        return table.add(injected).growX();
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name, boolean checkedByDefault,
            boolean isDisabled) {
        var checkBox = new CheckBox(" " + name, skin);
        checkBox.setChecked(checkedByDefault);
        checkBox.setDisabled(isDisabled);
        checkBox.align(Align.left);
        return groupTable.add(checkBox).growX();
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name, boolean checkedByDefault) {
        return checkBox(groupTable, name, checkedByDefault, false);
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name) {
        return checkBox(groupTable, name, false, false);
    }

    private Cell<Label> title(String text) {
        var title = label("-= " + text + " =-").colspan(4).padTop(35).padBottom(10);
        table.row();
        return title.align(Align.center);
    }

    private Cell<Label> label(String text) {
        Label label = new Label(text, skin);
        return table.add(label);
    }

    private Cell<Label> label(Table table, String text) {
        Label label = new Label(text, skin);
        return table.add(label);
    }

    private Cell<SelectBox<String>> selectBox(Table table, int index, String... items) {
        SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems(items);
        selectBox.setSelectedIndex(index);
        return table.add(selectBox);
    }


    public GeneratorConfigs extractConfigs() {
        GeneratorConfigs extracted = new GeneratorConfigs();

        /* Main settings */
        extracted.setLibGdxVersion(libGdxVersion.getText());
        extracted.setProjectName(projectName.getText());
        extracted.setCorePackage(corePackage.getText());
        extracted.setMainClass(mainClass.getText());
        extracted.setAssetsFolderName(assetsFolderName.getText());
        extracted.setJavaVersion(javaVersion.getText());

        /* Advanced settings */
        extracted.setApplicationVersion(applicationVersion.getText());
        extracted.setPostGenerationTask(postGenerationTask.getText());
        extracted.setServerJavaVersion(serverJavaVersion.getText());
        extracted.setDesktopJavaVersion(desktopJavaVersion.getText());
        extracted.setGwtPluginVersion(gwtPluginVersion.getText());
        extracted.setRoboVmVersion(roboVmVersion.getText());
        extracted.setAndroidSdkPath(androidSdkPath.getText());
        extracted.setAndroidPluginVersion(androidPluginVersion.getText());
        extracted.setTargetAndroidApi(Integer.parseInt(targetAndroidApi.getText()));

        /* Template */
        extracted.setTemplate(Template.fromString(templatesList.getSelected()));

        /* JVM Languages */
        Set<VersionedLanguage> jvmLanguages = new HashSet<>();
        addJvmLanguage(jvmLanguages, javaLanguage);
        addJvmLanguage(jvmLanguages, kotlinLanguage);
        addJvmLanguage(jvmLanguages, scalaLanguage);
        addJvmLanguage(jvmLanguages, groovyLanguage);
        extracted.setLanguages(jvmLanguages);

        /* Platforms */
        Set<Platform> platforms = new HashSet<>();
        addPlatform(platforms, corePlatform);
        addPlatform(platforms, androidPlatform);
        addPlatform(platforms, desktopPlatform);
        addPlatform(platforms, headlessPlatform);
        addPlatform(platforms, htmlPlatform);
        addPlatform(platforms, iosPlatform);
        addPlatform(platforms, lwjgl3Platform);
        addPlatform(platforms, serverPlatform);
        addPlatform(platforms, sharedPlatform);
        extracted.setPlatforms(platforms);

        /* AddOns */
        Set<AddOn> addOns = new HashSet<>();
        addAddOn(addOns, guiAssetsAddon);
        addAddOn(addOns, readMeAddon);
        addAddOn(addOns, gradleWrapperAddon);
        extracted.setAddOns(addOns);

        /* Third-Party libraries */
        var fakeDependencies = getRandomThirdParty(extracted.getLibGdxVersionObject(),
                Integer.parseInt(fakeJsonDependencies.getText()));
        extracted.getJsonLibraries().addAll(fakeDependencies);

        return extracted;
    }

    private void addJvmLanguage(Set<VersionedLanguage> collection, CheckBox checkBox) {
        if (checkBox.isChecked()) {
            var text = checkBox.getText().toString().trim();
            var lang = new VersionedLanguage(Language.fromString(text));
            collection.add(lang);
        }
    }

    private void addPlatform(Set<Platform> collection, CheckBox checkBox) {
        if (checkBox.isChecked()) {
            collection.add(Platform.fromString(checkBox.getText().toString().trim()));
        }
    }

    private void addAddOn(Set<AddOn> collection, CheckBox checkBox) {
        if (checkBox.isChecked()) {
            collection.add(AddOn.fromString(checkBox.getText().toString().trim()));
        }
    }

    private Collection<GdxThirdParty> getRandomThirdParty(LibGdxVersion libGdxVersion, int amount) {
        Collection<GdxThirdParty> libraries = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            GdxThirdParty library = new GdxThirdParty(1, "myName" + i, "myDesc", "myUrl");
            State randomState = new State(i + "." + (i + 1) + "." + (i + 3));
            randomState.getDesktopDependencies().add("random:desktop-dep" + i);
            randomState.getDesktopDependencies().add("random:desktop-dep" + i + ":natives-desktop");
            randomState.getDesktopDependencies().add("other.random:desk" + i);
            randomState.getDesktopDependencies().add("other.random:desk" + i + ":natives-desktop");
            randomState.getAndroidDependencies().add("some.stuff:for-android" + i);
            randomState.getAndroidDependencies().add("some.other.stuff:for-android" + i);
            randomState.getAndroidDependencies().add("some.more.stuff:for-android" + i);
            randomState.getAndroidNativeDependencies().add("and:whats-ewa" + i + ":natives-armeai");
            randomState.getAndroidNativeDependencies().add("and:whats-UWU" + i + ":natives-a-v7a");
            randomState.getIOSDependencies().add("ios.stuff:is-crap" + i);
            randomState.getIOSDependencies().add("ios.stuff:is-crap" + i + ":natives-ios");
            randomState.getGwtDependencies().add("amazing-stuff:might-suck" + i);
            randomState.getGwtDependencies().add("amazing-stuff:might-suck" + i + ":sources");
            randomState.getGwtDependencies().add("amazing-stuff:mightyy-suck" + i);
            randomState.getGwtDependencies().add("amazing-stuff:mightyy-suck" + i + ":sources");
            randomState.getCoreDependencies().add("com.rafaskoberg.gdx:typing-label" + i);
            randomState.getCoreDependencies().add("com.github.tommyettinger:regexodus" + i);
            library.getStates().put(libGdxVersion, randomState);
            libraries.add(library);
        }
        return libraries;
    }
}
