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

    /* Advanced settings */
    private TextField applicationVersion;
    private TextField postGenerationTask;
    private TextField serverJavaVersion;
    private TextField desktopJavaVersion;
    private TextField gwtPluginVersion;
    private TextField roboVmVersion;
    private TextField androidSdkPath;
    private TextField androidPluginVersion;

    private SelectBox<String> template;

//    private Tree<Test, String> addons;
//    static class Test extends Tree.Node<Test, String, Label> {
//    }

    private CheckBox readMeAddon;
    private CheckBox guiAssetsAddon;
    private CheckBox gradleWrapperAddon;

    public InputConfigsDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);
        scrollPane = new ScrollPane(table);
        generateBtn = new TextButton("GO", skin);

        configureTable();
//        init();
        setUp();
    }

    private void configureTable() {
        table.clearChildren();
        table.align(Align.topLeft);
        table.defaults().pad(4);

        table.setDebug(true);
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
        title("ADD ONS");

        Table addOns = new Table(skin);
        addOns.defaults().padLeft(30).padRight(30);
        checkBox(addOns, "GUI assets", guiAssetsAddon);
        checkBox(addOns, "ReadMe", readMeAddon);
        checkBox(addOns, "Gradle Wrapper", gradleWrapperAddon);
        table.add(addOns).colspan(4);

        table.row();
        title("PLATFORMS");

        Table platforms = new Table(skin);
        platforms.defaults().padLeft(30).padRight(30);
        checkBox(platforms, "GUI assets", guiAssetsAddon);
        checkBox(platforms, "ReadMe", readMeAddon);
        checkBox(platforms, "Gradle Wrapper", gradleWrapperAddon);
        table.add(platforms).colspan(4);

        table.row();
        title("JVM LANGUAGES");

        Table jvmLanguages = new Table(skin);
        jvmLanguages.defaults().padLeft(30).padRight(30);
        checkBox(jvmLanguages, "GUI assets", guiAssetsAddon);
        checkBox(jvmLanguages, "ReadMe", readMeAddon);
        checkBox(jvmLanguages, "Gradle Wrapper", gradleWrapperAddon);
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
        textField("androidSdkPath", androidSdkPath, "C:/something/somewhere");
        textField("androidPluginVersion", androidPluginVersion, defaults.getAndroidPluginVersion());
        table.row();
        // todo: SelectBox of Integers (targetAndroidApi)
        // todo: SelectBox of Integers (jsonLibraries) : amount of fake libs
    }

    private void textField(String name, TextField injected, String defaults) {
        label(name + ":").align(Align.left);
        injected = new TextField(defaults, skin);
        table.add(injected).growX();
    }

    private void checkBox(Table groupTable, String name, CheckBox checkBox) {
        checkBox = new CheckBox(name, skin);
        groupTable.add(checkBox).growX();
    }

    private void title(String text) {
        label("-= " + text + " =-").colspan(4).padTop(20).padBottom(20);
        table.row();
    }

    private Cell<Label> label(String text) {
        Label label = new Label(text, skin);
        return table.add(label);
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

        // todo: finish the mapping

        return extracted;
    }

//    @Deprecated
//    private void init() {
//        var defaults = new GeneratorConfigs();
//
//        boolean newRow = false;
//        Field[] fields = GeneratorConfigs.class.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            var label = new Label(field.getName() + ": ", skin);
//            boolean isString = field.getType().getSimpleName().equals("String");
//            TextField input;
//            try {
//                boolean nonNull = field.get(defaults) != null;
//                input = new TextField(isString && nonNull ? field.get(defaults).toString() : "",
//                        skin);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//                input = new TextField("SOME ERROR HAPPENED", skin);
//            }
//
//            table.add(label).align(Align.left);
//            table.add(input).growX();
//            if (newRow) {
//                table.row();
//            }
//            newRow = !newRow;
//        }
//    }
}
