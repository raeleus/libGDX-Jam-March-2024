package com.ray3k.zebradilemma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.ray3k.zebradilemma.Core.*;

public class BuildScreen extends ScreenAdapter {
    private Stage stage;
    private int counter;

    @Override
    public void show() {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        var root = new Table();
        root.setFillParent(true);
        root.setTouchable(Touchable.enabled);
        stage.addActor(root);

        counter = 0;
        var textButton = new TextButton("Run Strategy", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (counter++) {
                    case 0:
                        textButton.setText("Actually, this concept was boring AF");
                        break;
                    case 1:
                        textButton.setText("I spent a whole week not wanting to work on this");
                        break;
                    case 2:
                        textButton.setText("On the final day, I said screw this");
                        break;
                    case 3:
                        textButton.setText("I made the rest of this in about 24 hours");
                        break;
                    case 4:
                        textButton.setText("Type the words to win!");
                        break;
                    case 5:
                        game.setScreen(new GameScreen());
                        break;
                }
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
