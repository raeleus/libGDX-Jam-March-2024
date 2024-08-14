package com.ray3k.zebradilemma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.ray3k.zebradilemma.SpineImage;

import static com.ray3k.zebradilemma.Core.*;

public class LibgdxScreen extends ScreenAdapter {
    private Stage stage;
    private SpineImage image;

    @Override
    public void show() {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        var skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal("spine/libgdx.json"));
        var animationStateData = new AnimationStateData(skeletonData);
        image = new SpineImage(skeletonRenderer, skeletonData, animationStateData);
        image.setCrop(0, 0, 1024, 576);
        image.setScaling(Scaling.fit);
        image.setFillParent(true);
        image.getAnimationState().setAnimation(0, "stand", false);
        image.getAnimationState().addAnimation(0, "animation", false, 0);
        image.getAnimationState().addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                var audioPath = event.getData().getAudioPath();
                if (audioPath != null) {
                    var sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + audioPath));
                    sound.play();
                }
            }

            @Override
            public void complete(TrackEntry entry) {
                if (entry.getAnimation().getName().equals("animation")) nextScreen();
            }
        });

        image.setTouchable(Touchable.enabled);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextScreen();
            }
        });
        image.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                nextScreen();
                return true;
            }
        });
        stage.setKeyboardFocus(image);
        stage.addActor(image);
    }

    private void nextScreen() {
        game.setScreen(new Ray3kScreen());
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
