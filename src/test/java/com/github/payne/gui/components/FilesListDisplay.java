package com.github.payne.gui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import lombok.Data;

@Data
public class FilesListDisplay {

    private final Skin skin;
    private final Table table;
    private final ScrollPane scrollPane;

    public FilesListDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);

        scrollPane = new ScrollPane(table);
        table.add(new Label("Files will appear here", skin)).grow();
        table.align(Align.topLeft).defaults().pad(2.5f);
    }

    public void clear() {
        table.clearChildren();
        resetScroll();
    }

    public Cell<Button> add(Button btn) {
        return table.add(btn);
    }

    public void resetScroll() {
        scrollPane.setScrollX(0);
        scrollPane.setScrollY(0);
    }
}
