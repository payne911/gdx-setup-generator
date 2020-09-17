package com.github.payne.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.payne.generator.Generator;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.FileNode;

public class Visualizer extends Game {

    private static final String SKIN_PATH = "visualizer-skin/skin.json";

    public Stage stage;
    public Table main;
    public Skin skin;

    public Generator generator = new Generator();
    public GeneratorConfigs input;
    public GeneratedProject output;
    public FileNode root;

    Widget fileContent;
    Widget fileFullPath;
    Table clickableVfs;
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
        main.setDebug(true);

        clickableVfs = new Table(skin);
        Table fileContentTable = new Table(skin);
        ScrollPane pane1 = new ScrollPane(clickableVfs);
        SplitPane topSplit = new SplitPane(pane1, fileContentTable, false, skin);
        ScrollPane inputPane = new ScrollPane(new Label("Configuration file!\n".repeat(40), skin));
        bottomSplit = new SplitPane(topSplit, inputPane, true, skin);

        int pad = 10;
        clickableVfs
                .add(new Label("Files will appear here after configuration.\n".repeat(40), skin))
                .grow().pad(pad);
        fileFullPath = new Label("Here will appear the full path of the selected file.", skin);
        fileContent = new Label("Click on a file (on the left) to see its content.\n".repeat(40),
                skin);
        ScrollPane pane2 = new ScrollPane(fileContent);
        fileContentTable.add(fileFullPath).growX().pad(pad).row();
        fileContentTable.add(pane2).grow().pad(pad);

        topSplit.setMinSplitAmount(.2f);
        topSplit.setMaxSplitAmount(.8f);
        topSplit.setSplitAmount(.25f);
        bottomSplit.setMinSplitAmount(.1f);
        bottomSplit.setMaxSplitAmount(.9f);
        bottomSplit.setSplitAmount(.3f);

        main.add(bottomSplit).grow();

        prepareModel();
    }

    private void prepareModel() {
        input = new GeneratorConfigs();
        input.setProjectName("awesome-project");

        output = generator.generateFileStructure(input);
        root = output.getVirtualFileSystem().getRoot();
        root.getChildren().forEach(this::navigate);
    }

    private void navigate(FileNode node) {
        // todo (raeleus): do stuff here
        node.getChildren().forEach(this::navigate); // recursive call through the tree
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