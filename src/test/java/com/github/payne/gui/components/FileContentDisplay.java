package com.github.payne.gui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import lombok.Data;

/**
 * Panel at the top-right. Displays the content of a selected file, along with its path.
 */
@Data
public class FileContentDisplay {

    private final Skin skin;
    private final Table table;
    private final ScrollPane scrollPane;

    private Label fullPath;
    private Label content;

    public FileContentDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);

        content = new Label("Click on a file (on the left) to see its content", skin);
        content.setAlignment(Align.topLeft);

        scrollPane = new ScrollPayne(content, skin);
        scrollPane.setOverscroll(false, false);
        fullPath = new Label("Full path of the selected file", skin);

        table.add(fullPath).padBottom(25).growX().row();
        table.add(scrollPane).grow();
        table.defaults().pad(10);
    }

    public void clear() {
        content.setText("");
        fullPath.setText("");
        resetScroll();
    }

    public void resetScroll() {
        scrollPane.setScrollX(0);
        scrollPane.setScrollY(0);
    }
}
