package com.github.payne.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.payne.generator.Generator;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.ui.components.InputConfigsDisplay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Visualizer extends Game {

    private static final String SKIN_PATH = "visualizer-skin/skin.json";

    public Stage stage;
    public Table main;
    public Skin skin;

    public Generator generator = new Generator();
    public GeneratorConfigs input;
    public GeneratedProject output;

    Label fileContent;
    Label fileFullPath;
    Table filesList;
    SplitPane bottomSplit;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal(SKIN_PATH));
        stage = new Stage(new ScreenViewport());
        main = new Table(skin);
        main.setFillParent(true);
        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);

        setUp();
    }

    private void setUp() {
        filesList = new Table(skin);
        Table fileContentTable = new Table(skin);
        ScrollPane filesListScrollPane = new ScrollPane(filesList);
        SplitPane topSplit = new SplitPane(filesListScrollPane, fileContentTable, false, skin);
        Table bottomTable = new Table(skin);
        bottomSplit = new SplitPane(topSplit, bottomTable, true, skin);

        filesList.add(new Label("Files will appear here", skin)).grow();
        fileFullPath = new Label("Full path of the selected file", skin);
        fileContent = new Label("Click on a file (on the left) to see its content", skin);
        fileContent.setAlignment(Align.topLeft);
        ScrollPane fileContentScrollPane = new ScrollPane(fileContent);
        fileContentTable.add(fileFullPath).padBottom(25).growX().row();
        fileContentTable.add(fileContentScrollPane).grow();

        Table inputTable = new Table(skin);
        ScrollPane inputPane = new ScrollPane(inputTable);
        Button generateBtn = new TextButton("GO", skin);
        bottomTable.add(inputPane).grow();
        bottomTable.add(generateBtn).width(160).growY();

        int pad = 10;
        fileContentTable.defaults().pad(pad);
        bottomTable.defaults().pad(pad);
        filesList.align(Align.topLeft).defaults().pad(2.5f);

        topSplit.setMinSplitAmount(.2f);
        topSplit.setMaxSplitAmount(.8f);
        topSplit.setSplitAmount(.25f);
        bottomSplit.setMinSplitAmount(.1f);
        bottomSplit.setMaxSplitAmount(.9f);
        bottomSplit.setSplitAmount(.15f);

        main.add(bottomSplit).grow();

        displayConfigs(inputTable);

        generateBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                filesList.clearChildren();
                fileContent.setText("");
                fileFullPath.setText("");
                bottomSplit.setSplitAmount(.9f);
                generate();
                fileContentScrollPane.setScrollBarPositions(false, false);
            }
        });
    }

    private void displayConfigs(Table table) {
        InputConfigsDisplay inputs = new InputConfigsDisplay(skin, table);
        inputs.init();
    }

    private void generate() {
        input = new GeneratorConfigs(); // todo: construct from the InputConfigsDisplay values!
        input.setProjectName("MyGdxProjectName");
        input.setCorePackage("com.foo");
        input.setAndroidSdkPath("C:/BobSdk");
        input.setTemplate(Template.CLASSIC);
        input.getPlatforms().addAll(Arrays
                .asList(Platform.CORE, Platform.ANDROID, Platform.HTML, Platform.SERVER,
                        Platform.DESKTOP_LEGACY, Platform.HEADLESS, Platform.IOS,
                        Platform.LWJGL_3, Platform.SHARED));
        input.getAddOns()
                .addAll(Arrays.asList(AddOn.GRADLE_WRAPPER, AddOn.GUI_ASSETS, AddOn.README));
        input.getLanguages().addAll(Arrays.asList(new VersionedLanguage(Language.JAVA),
                new VersionedLanguage(Language.KOTLIN), new VersionedLanguage(Language.GROOVY),
                new VersionedLanguage(Language.SCALA)));
        input.getJsonLibraries().addAll(getRandomThirdParty(3));

        output = generator.generateFileStructure(input);
        output.getVirtualFileSystem().depthFirstTraversal((node, depth) -> {
            var fileBtn = new TextButton(prefix(node) + node.getName(), skin);
            filesList.add(fileBtn).padLeft(depth * 20).growX().row();
            fileBtn.getLabel().setAlignment(Align.left);
            fileBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    fileFullPath.setText(node.getFullPath());
                    fileContent.setText(node.isFolder() ? "" : new String(node.getContent()));
                }
            });
        });
    }

    private Collection<GdxThirdParty> getRandomThirdParty(int amount) {
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
            library.getStates().put(input.getLibGdxVersionObject(), randomState);
            libraries.add(library);
        }
        return libraries;
    }

    private String prefix(FileNode node) {
        return (node.isFolder() ? "[X]" : "") + " ";
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.8f, .8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}