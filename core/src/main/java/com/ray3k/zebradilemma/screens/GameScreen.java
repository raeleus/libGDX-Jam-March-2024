package com.ray3k.zebradilemma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Event;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.zebradilemma.SpineImage;

import static com.ray3k.zebradilemma.Core.*;

public class GameScreen extends ScreenAdapter {
    private Stage stage;
    private SpineImage image;
    private Bone cameraBone;
    private Bone textBone;
    private TypingLabel label;
    private String targetText;
    private String typed;
    private boolean allowTyping;
    private int index = 0;

    @Override
    public void show() {
        music.play();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        label = new TypingLabel("", skin, "game-text");

        var skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal("spine/action.json"));
        var animationStateData = new AnimationStateData(skeletonData);
        image = new SpineImage(skeletonRenderer, skeletonData, animationStateData);
        image.setCrop(0, 0, 1024, 576);
        image.setScaling(Scaling.fit);
        image.setFillParent(true);
        image.getAnimationState().setAnimation(0, "anim-01", false);
        image.getAnimationState().addListener(new AnimationStateAdapter() {
            @Override
            public void event(TrackEntry entry, Event event) {
                var audioPath = event.getData().getAudioPath();
                if (audioPath != null) {
                    var sound = Gdx.audio.newSound(Gdx.files.internal("sfx/" + audioPath));
                    sound.play();
                } else if (event.getData().getName().equals("text")) {
                    Gdx.app.postRunnable(() -> {
                        var text = event.getString();

                        if (text == null || text.equals("")) {
                            allowTyping = false;
                            label.setText("");
                            return;
                        }

                        targetText = text;
                        typed = "";
                        allowTyping = true;
                        label.setText(text);
                        label.skipToTheEnd();
                    });
                }
            }

            @Override
            public void complete(TrackEntry entry) {
                var passed = typed.equals(targetText);

                switch (entry.getAnimation().getName()) {
                    case "anim-01":
                        image.getAnimationState().addAnimation(0, passed ? "anim-02" : "fail-01", false, 0);
                        break;
                    case "anim-02":
                        image.getAnimationState().addAnimation(0, passed ? "anim-03" : "fail-02", false, 0);
                        break;
                    case "anim-03":
                        image.getAnimationState().addAnimation(0, passed ? "anim-04" : "fail-03", false, 0);
                        break;
                    case "anim-04":
                        image.getAnimationState().addAnimation(0, passed ? "anim-05" : "fail-04", false, 0);
                        break;
                    case "anim-05":
                        image.getAnimationState().addAnimation(0, passed ? "anim-06" : "fail-05", false, 0);
                        break;
                    case "anim-06":
                        image.getAnimationState().addAnimation(0, passed ? "anim-07" : "fail-06", false, 0);
                        break;
                    case "anim-07":
                        image.getAnimationState().addAnimation(0, passed ? "anim-08" : "fail-07", false, 0);
                        break;
                    case "anim-08":
                        image.getAnimationState().addAnimation(0, passed ? "anim-09" : "fail-08", false, 0);
                        break;
                    case "anim-09":
                        nextScreen();
                        break;
                    default:
                        game.setScreen(new GameScreen());
                        break;
                }
            }
        });

        image.setTouchable(Touchable.enabled);
        image.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (allowTyping) {
                    typed += character;
                    if (!targetText.startsWith(typed)) {
                        label.setText("{COLOR=RED}FAILED");
                        allowTyping = false;
                    } else if (targetText.equals(typed)) {
                        label.setText("{COLOR=GREEN}SUCCESS");
                        allowTyping = false;
                    } else {
                        label.setText("{COLOR=BLUE}" + typed + "{COLOR=WHITE}" + targetText.substring(typed.length()));
                    }
                    label.skipToTheEnd();
                }
                return true;
            }
        });

        stage.setKeyboardFocus(image);
        stage.addActor(image);
        stage.addActor(label);

        cameraBone = image.getSkeleton().findBone("camera");
        textBone = image.getSkeleton().findBone("text");
    }

    private void nextScreen() {
        game.setScreen(new CreditsScreen());
    }

    @Override
    public void render(float delta) {
        camera.zoom = 1 / cameraBone.getScaleX();
        camera.position.set(cameraBone.getWorldX(), cameraBone.getWorldY(), 1);

        label.setPosition(textBone.getWorldX(), textBone.getWorldY(), Align.center);

        ScreenUtils.clear(Color.BLACK);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
