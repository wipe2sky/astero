package com.kurtsevich.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kurtsevich.asteroids.adapter.KeyboardAdapter;
import com.kurtsevich.asteroids.screen.MainMenuScreen;

public class AsterGame extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public SpriteBatch batch;
    public KeyboardAdapter inputProcessor = new KeyboardAdapter();


    @Override
    public void create() {
        Gdx.input.setInputProcessor(inputProcessor);
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}