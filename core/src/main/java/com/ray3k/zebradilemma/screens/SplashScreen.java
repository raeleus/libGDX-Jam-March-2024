package com.ray3k.zebradilemma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.ray3k.zebradilemma.Core.*;

public class SplashScreen extends ScreenAdapter {
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        var table = new Table();
        table.setFillParent(true);
        table.setTouchable(Touchable.enabled);
        stage.addActor(table);

        var label = new Label("Click to begin", skin);
        table.add(label);

        table.addListener(new ClickListener(-1) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getButton() == Buttons.RIGHT) game.setScreen(new BuildScreen());
                else game.setScreen(new LibgdxScreen());
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
