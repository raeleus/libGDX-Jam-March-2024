package com.ray3k.zebradilemma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import com.ray3k.zebradilemma.screens.SplashScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game {
    public static FitViewport viewport;
    public static OrthographicCamera camera;
    public static TwoColorPolygonBatch batch;
    public static SkeletonRenderer skeletonRenderer;
    public static SkeletonJson skeletonJson;
    public static TextureAtlas textureAtlas;
    public static Skin skin;
    public static Game game;
    public static Music music;

    @Override
    public void create() {
        game = this;
        music = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm.mp3"));
        batch = new TwoColorPolygonBatch(32767);
        camera = new OrthographicCamera();
        viewport = new FitViewport(1024, 576, camera);
        skeletonRenderer = new SkeletonRenderer();
        textureAtlas = new TextureAtlas(Gdx.files.internal("textures/textures.atlas"));
        skeletonJson = new SkeletonJson(textureAtlas);
        skin = new Skin(Gdx.files.internal("skin/skin.json"), textureAtlas);

        setScreen(new SplashScreen());
    }
}
