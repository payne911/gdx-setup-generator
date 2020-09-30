package com.github.payne.ui.components;

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

    private Label fileFullPath;
    private Label fileContent;
    private ScrollPane scrollPane;

    public void init() {
        fileContent = new Label("Click on a file (on the left) to see its content", skin);
        fileContent.setAlignment(Align.topLeft);

        scrollPane = new ScrollPane(fileContent);
        fileFullPath = new Label("Full path of the selected file", skin);

        table.add(fileFullPath).padBottom(25).growX().row();
        table.add(scrollPane).grow();
    }
}
