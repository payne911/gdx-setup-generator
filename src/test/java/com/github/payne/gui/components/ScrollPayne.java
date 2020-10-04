package com.github.payne.gui.components;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Null;

/**
 * Essentially a replica of {@link ScrollPane}, but with the objective of preventing scrolls to
 * happen when using the middle or right mouse click buttons.
 */
public class ScrollPayne extends ScrollPane {

    /**
     * @param widget May be null.
     */
    public ScrollPayne(@Null Actor widget, Skin skin) {
        this(widget, skin.get(ScrollPaneStyle.class));
    }

    /**
     * @param widget May be null.
     */
    public ScrollPayne(@Null Actor widget, ScrollPaneStyle style) {
        super(widget, style);

        addCaptureListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == 0 && button != 0) {
                    event.cancel(); // ensure only a left-click will pan
                    return false;
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
