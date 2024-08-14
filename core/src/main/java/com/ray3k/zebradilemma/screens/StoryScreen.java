package com.ray3k.zebradilemma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.zebradilemma.SpineImage;

import static com.ray3k.zebradilemma.Core.*;

public class StoryScreen extends ScreenAdapter {
    private Stage stage;
    private SpineImage image;
    private TypingLabel typingLabel;
    private ObjectMap<Sound, Long> sounds;
    private Music speech;

    @Override
    public void show() {
        sounds = new ObjectMap<>();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        var skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal("spine/story.json"));
        var animationStateData = new AnimationStateData(skeletonData);
        image = new SpineImage(skeletonRenderer, skeletonData, animationStateData);
        image.setCrop(0, 0, 1024, 576);
        image.setScaling(Scaling.fit);
        image.setFillParent(true);
        image.getAnimationState().setAnimation(0, "animation", false);
        image.getAnimationState().addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                var audioPath = event.getData().getAudioPath();
                if (audioPath != null) {
                    if (audioPath.equals("story.mp3")) {
                        speech = Gdx.audio.newMusic(Gdx.files.internal("sfx/story.mp3"));
                        speech.play();
                    } else {
                        var sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + audioPath));
                        var id = sound.play();
                        sounds.put(sound, id);
                    }
                } else if (event.getData().getName().equals("text")){
                    typingLabel.restart(event.getString());
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

        var table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        typingLabel = new TypingLabel("", skin);
        table.add(typingLabel).top().padTop(50).expand();
    }

    private void nextScreen() {
        if (speech != null) speech.stop();
        for (var sound : sounds) {
            sound.key.stop(sound.value);
        }
        game.setScreen(new BuildScreen());
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
