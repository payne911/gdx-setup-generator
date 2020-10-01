package com.github.payne.ui.components;

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
import lombok.Data;

@Data
public class InputConfigsDisplay {

    private final Skin skin;
    private final Table table;
    private final ScrollPane scrollPane;
    private final Button generateBtn;

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

    public InputConfigsDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);
        scrollPane = new ScrollPane(table);
        generateBtn = new TextButton("GO", skin);

        configureTable();
        setUp();
    }

    private void configureTable() {
        table.clearChildren();
        table.align(Align.topLeft);
        table.defaults().align(Align.left).pad(4);

//        table.setDebug(true); // todo: comment
    }

    private void setUp() {
        var defaults = new GeneratorConfigs(); // dummy to get default values

        title("MAIN SETTINGS");
        textField("libGdxVersion", libGdxVersion, defaults.getLibGdxVersion());
        textField("projectName", projectName, defaults.getProjectName());
        table.row();
        textField("corePackage", corePackage, defaults.getCorePackage());
        textField("mainClass", mainClass, defaults.getMainClass());
        table.row();
        textField("assetsFolderName", assetsFolderName, defaults.getAssetsFolderName());
        textField("javaVersion", javaVersion, defaults.getJavaVersion());

        table.row();
        title("MODULES");
        Table platforms = new Table(skin);
        platforms.defaults().padLeft(30).padRight(30).align(Align.left);
        label(platforms, "Select a platform: ");
        checkBox(platforms, "android", androidPlatform);
        checkBox(platforms, "desktop", desktopPlatform);
        checkBox(platforms, "lwjgl3", lwjgl3Platform, true);
        checkBox(platforms, "html", htmlPlatform);
        checkBox(platforms, "headless", headlessPlatform);
        checkBox(platforms, "ios", iosPlatform);
        platforms.row();
        label(platforms, "Extra modules: ");
        checkBox(platforms, "core", corePlatform, true, true);
        checkBox(platforms, "server", serverPlatform);
        checkBox(platforms, "shared", sharedPlatform).colspan(4);
        table.add(platforms).colspan(4);

        table.row();
        title("TEMPLATE");
        Table templates = new Table(skin);
        templates.defaults().padLeft(30).padRight(30);
        label(templates, "Select a template: ");
        selectBox(templates, templatesList, 2, "application-adapter", "application-listener",
                "classic", "game", "input-processor", "scene2d");
        table.add(templates).colspan(4);

        table.row();
        title("ADD ONS");
        Table addOns = new Table(skin);
        addOns.defaults().padLeft(30).padRight(30);
        checkBox(addOns, "GUI assets", guiAssetsAddon, true);
        checkBox(addOns, "ReadMe", readMeAddon, true);
        checkBox(addOns, "Gradle Wrapper", gradleWrapperAddon);
        table.add(addOns).colspan(4);

        table.row();
        title("JVM LANGUAGES");
        Table jvmLanguages = new Table(skin);
        jvmLanguages.defaults().padLeft(30).padRight(30);
        checkBox(jvmLanguages, "java", javaLanguage, true, true);
        checkBox(jvmLanguages, "kotlin", kotlinLanguage);
        checkBox(jvmLanguages, "scala", scalaLanguage);
        checkBox(jvmLanguages, "groovy", groovyLanguage);
        table.add(jvmLanguages).colspan(4);

        table.row();
        title("ADVANCED SETTINGS");
        textField("applicationVersion", applicationVersion, defaults.getApplicationVersion());
        textField("postGenerationTask", postGenerationTask, defaults.getPostGenerationTask());
        table.row();
        textField("serverJavaVersion", serverJavaVersion, defaults.getServerJavaVersion());
        textField("desktopJavaVersion", desktopJavaVersion, defaults.getDesktopJavaVersion());
        table.row();
        textField("gwtPluginVersion", gwtPluginVersion, defaults.getGwtPluginVersion());
        textField("roboVmVersion", roboVmVersion, defaults.getRoboVmVersion());
        table.row();
        textField("androidSdkPath", androidSdkPath, "C:/something/somewhere/sdk");
        textField("androidPluginVersion", androidPluginVersion, defaults.getAndroidPluginVersion());
        table.row();
        textField("targetAndroidApi", targetAndroidApi, defaults.getTargetAndroidApi().toString());
        // todo: SelectBox of Integers (jsonLibraries) : amount of fake libs ?
    }

    private Cell<TextField> textField(String name, TextField injected, String defaults) {
        label(name + ":").align(Align.left);
        injected = new TextField(defaults, skin);
        return table.add(injected).growX();
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name, CheckBox checkBox,
            boolean checkedByDefault, boolean isDisabled) {
        checkBox = new CheckBox(" " + name, skin);
        checkBox.setChecked(checkedByDefault);
        checkBox.setDisabled(isDisabled);
        checkBox.align(Align.left);
        return groupTable.add(checkBox).growX();
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name, CheckBox checkBox,
            boolean checkedByDefault) {
        return checkBox(groupTable, name, checkBox, checkedByDefault, false);
    }

    private Cell<CheckBox> checkBox(Table groupTable, String name, CheckBox checkBox) {
        return checkBox(groupTable, name, checkBox, false, false);
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

    private Cell<SelectBox> selectBox(Table table, SelectBox selectBox, int index,
            String... items) {
        selectBox = new SelectBox<>(skin);
        selectBox.setItems(items);
        selectBox.setSelectedIndex(index);
        return table.add(selectBox);
    }


    public GeneratorConfigs extractConfigs() {
        GeneratorConfigs extracted = new GeneratorConfigs();

        extracted.setLibGdxVersion(libGdxVersion.getText());
        extracted.setProjectName(projectName.getText());
        extracted.setCorePackage(corePackage.getText());
        extracted.setMainClass(mainClass.getText());
        extracted.setAssetsFolderName(assetsFolderName.getText());
        extracted.setJavaVersion(javaVersion.getText());

        extracted.setApplicationVersion(applicationVersion.getText());
        extracted.setPostGenerationTask(postGenerationTask.getText());
        extracted.setServerJavaVersion(serverJavaVersion.getText());
        extracted.setDesktopJavaVersion(desktopJavaVersion.getText());
        extracted.setGwtPluginVersion(gwtPluginVersion.getText());
        extracted.setRoboVmVersion(roboVmVersion.getText());
        extracted.setAndroidSdkPath(androidSdkPath.getText());
        extracted.setAndroidPluginVersion(androidPluginVersion.getText());
        extracted.setTargetAndroidApi(Integer.valueOf(targetAndroidApi.getText()));

        // todo: finish the mapping

        return extracted;
    }
}
