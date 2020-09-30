package com.github.payne.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.github.payne.generator.input.GeneratorConfigs;
import java.lang.reflect.Field;
import lombok.Data;

@Data
public class InputConfigsDisplay {

    private final Skin skin;
    private final Table table;
    private final ScrollPane scrollPane;
    private final Button generateBtn;

    public InputConfigsDisplay(Skin skin) {
        this.skin = skin;
        table = new Table(skin);
        scrollPane = new ScrollPane(table);
        generateBtn = new TextButton("GO", skin);

        init();
    }

    private void init() {
        table.clearChildren();
        table.align(Align.topLeft);
        table.defaults().pad(4);

        var defaults = new GeneratorConfigs();

        boolean newRow = false;
        Field[] fields = GeneratorConfigs.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            var label = new Label(field.getName() + ": ", skin);
            boolean isString = field.getType().getSimpleName().equals("String");
            TextField input;
            try {
                boolean nonNull = field.get(defaults) != null;
                input = new TextField(isString && nonNull ? field.get(defaults).toString() : "",
                        skin);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                input = new TextField("SOME ERROR HAPPENED", skin);
            }

            table.add(label).align(Align.left);
            table.add(input).growX();
            if (newRow) {
                table.row();
            }
            newRow = !newRow;
        }
    }
}
