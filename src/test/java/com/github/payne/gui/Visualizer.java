package com.github.payne.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.payne.generator.Generator;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.gui.components.FileContentDisplay;
import com.github.payne.gui.components.FilesListDisplay;
import com.github.payne.gui.components.InputConfigsDisplay;
import com.payne.games.piemenu.PieMenu;

public class Visualizer extends Game {

    private static final String SKIN_PATH = "visualizer-skin/skin.json";

    public Stage stage;
    public Table main;
    public Skin skin;

    public PieMenu menu;

    public Generator generator = new Generator();
    public GeneratorConfigs input;
    public GeneratedProject output;

    public FileContentDisplay contentDisplay;
    public FilesListDisplay listDisplay;
    public InputConfigsDisplay configsDisplay;

    public SplitPane bottomSplit;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal(SKIN_PATH));
        stage = new Stage(new ScreenViewport());
        main = new Table(skin);
        main.setFillParent(true);
        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);

        setUpComponents();
        pieMenu();
    }

    private void setUpComponents() {
        contentDisplay = new FileContentDisplay(skin);
        listDisplay = new FilesListDisplay(skin);
        configsDisplay = new InputConfigsDisplay(skin);

        SplitPane topSplit = new SplitPane(listDisplay.getScrollPane(),
                contentDisplay.getTable(), false, skin);
        Table bottomTable = new Table(skin);
        bottomSplit = new SplitPane(topSplit, bottomTable, true, skin);

        bottomTable.add(configsDisplay.getScrollPane()).grow();
        bottomTable.add(configsDisplay.getGenerateBtn()).width(160).growY();
        bottomTable.defaults().pad(10);

        topSplit.setMinSplitAmount(.2f);
        topSplit.setMaxSplitAmount(.8f);
        topSplit.setSplitAmount(.25f);
        bottomSplit.setMinSplitAmount(.1f);
        bottomSplit.setMaxSplitAmount(.9f);
        bottomSplit.setSplitAmount(.15f);

        main.add(bottomSplit).grow();

        configsDisplay.getGenerateBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listDisplay.clear();
                contentDisplay.clear();
                bottomSplit.setSplitAmount(.9f);
                generate();
            }
        });
    }

    private void generate() {
        input = configsDisplay.extractConfigs();
        output = generator.generateFileStructure(input);
        output.getVirtualFileSystem().depthFirstTraversal((node, depth) -> {
            var fileBtn = new TextButton(prefix(node) + node.getName(), skin);
            listDisplay.add(fileBtn).padLeft(depth * 20).growX().row();
            fileBtn.getLabel().setAlignment(Align.left);
            fileBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    contentDisplay.resetScroll();
                    contentDisplay.getFullPath().setText(node.getFullPath());
                    contentDisplay.getContent()
                            .setText(node.isFolder() ? "" : new String(node.getContent()));
                }
            });
        });
    }

    private String prefix(FileNode node) {
        return (node.isFolder() ? "[X]" : "") + " ";
    }

    /**
     * Model taken from:  https://github.com/payne911/PieMenu/wiki/Examples#click-drag
     */
    private void pieMenu() {
        PieMenu.PieMenuStyle style = new PieMenu.PieMenuStyle();
        style.separatorWidth = 2;
        style.circumferenceWidth = 2;
        style.backgroundColor = new Color(1, 1, 1, .5f);
        style.downColor = new Color(.7f, .2f, .2f, 1);
        style.sliceColor = new Color(.6f, .1f, .1f, 1);
        menu = new PieMenu(skin.getRegion("white"), style, 110, .33f);

        menu.setInfiniteSelectionRange(true);
        menu.setSelectionButton(Input.Buttons.RIGHT); // right-click for interactions
        menu.setMiddleCancel(true);

        menu.addActor(new Label("EXPAND", skin));
        menu.addActor(new Label("HIDE", skin));

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (menu.getSelectedIndex() == 0) {
                    bottomSplit.setSplitAmount(.1f);
                } else if (menu.getSelectedIndex() == 1) {
                    bottomSplit.setSplitAmount(.9f);
                }

                menu.setVisible(false);
                menu.remove();
                menu.resetSelection();
            }
        });
    }

    /**
     * To be used to get the user to transition directly into {@code InputListener#touchDragged(InputEvent,
     * float, float, int)} as if he had triggered {@code InputListener#touchDown(InputEvent, float,
     * float, int, int)}.
     * <p>
     * I am not certain this is the recommended way of doing this, but for the purposes of this
     * demonstration, it works!
     *
     * @param stage  the stage.
     * @param widget the PieMenu on which to transfer the interaction.
     */
    private void transferInteraction(Stage stage, PieMenu widget) {
        stage.addTouchFocus(widget.getPieMenuListener(), widget, widget, 0,
                widget.getSelectionButton());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.8f, .8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

        stage.act();
        stage.draw();

        if (Gdx.input.isButtonJustPressed(menu.getSelectionButton())) {
            stage.addActor(menu);
            menu.centerOnMouse();
            menu.setVisible(true);
            transferInteraction(stage, menu);
        }
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