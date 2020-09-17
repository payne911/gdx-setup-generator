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

    Label fileContent;
    Label fileFullPath;
    Table fileList;
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

        fileList = new Table(skin);
        Table fileContentTable = new Table(skin);
        ScrollPane pane1 = new ScrollPane(fileList);
        SplitPane topSplit = new SplitPane(pane1, fileContentTable, false, skin);
        Table bottomTable = new Table(skin);
        bottomSplit = new SplitPane(topSplit, bottomTable, true, skin);

        fileList
                .add(new Label("Files will appear here after configuration.\n".repeat(40), skin))
                .grow();
        fileFullPath = new Label("Here will appear the full path of the selected file.", skin);
        fileContent = new Label("Click on a file (on the left) to see its content.\n".repeat(40),
                skin);
        ScrollPane pane2 = new ScrollPane(fileContent);
        fileContentTable.add(fileFullPath).growX().row();
        fileContentTable.add(pane2).grow();

        Table inputTable = new Table(skin);
        inputTable.add(new Label("Configuration file!\n".repeat(40), skin));
        ScrollPane inputPane = new ScrollPane(inputTable);
        Button generateBtn = new TextButton("GO", skin);
        bottomTable.add(inputPane).grow();
        bottomTable.add(generateBtn).growY();

        int pad = 10;
        fileList.defaults().pad(pad);
        fileContentTable.defaults().pad(pad);
        bottomTable.defaults().pad(pad);

        topSplit.setMinSplitAmount(.2f);
        topSplit.setMaxSplitAmount(.8f);
        topSplit.setSplitAmount(.25f);
        bottomSplit.setMinSplitAmount(.1f);
        bottomSplit.setMaxSplitAmount(.9f);
        bottomSplit.setSplitAmount(.3f);

        main.add(bottomSplit).grow();

        generateBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fileList.clearChildren();
                bottomSplit.setSplitAmount(.9f);
                generate();
            }
        });
    }

    private void generate() {
        input = new GeneratorConfigs();
        input.setProjectName("awesome-project");

        output = generator.generateFileStructure(input);
        root = output.getVirtualFileSystem().getRoot();
        root.getChildren().forEach(this::navigate);
    }

    private void navigate(FileNode node) {
        System.out.println(node);

        Button fileBtn = new TextButton(markFolder(node) + node.getName(), skin);
        fileList.add(fileBtn).row();
        fileBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fileFullPath.setText(node.getFullPath());
                fileContent.setText(node.isFolder() ? "" : new String(node.getContent()));
            }
        });

        node.getChildren().forEach(this::navigate); // recursive call through the sorted tree
    }

    private String markFolder(FileNode node) {
        return "[" + (node.isFolder() ? "X" : " ") + "] ";
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