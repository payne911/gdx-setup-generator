package com.github.payne;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
        main.defaults().pad(20);
        main.add("Welcome to the almighty Zebra!")
                .expandX()
                .pad(10)
                .getActor().setFontScale(1.2f);

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