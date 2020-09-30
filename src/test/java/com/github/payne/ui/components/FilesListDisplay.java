package com.github.payne.ui.components;

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

    private ScrollPane filesListScrollPane;

    public FilesListDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);

        filesListScrollPane = new ScrollPane(table);
        table.add(new Label("Files will appear here", skin)).grow();
        table.align(Align.topLeft).defaults().pad(2.5f);
    }

    public void clear() {
        table.clearChildren();
    }

    public Cell<Button> add(Button btn) {
        return table.add(btn);
    }
}
