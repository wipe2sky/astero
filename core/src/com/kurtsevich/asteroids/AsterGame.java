package com.kurtsevich.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kurtsevich.asteroids.adapter.KeyboardAdapter;
import com.kurtsevich.asteroids.screen.MainMenuScreen;

public class AsterGame extends Game {
    public SpriteBatch batch;
    public KeyboardAdapter inputProcessor = new KeyboardAdapter();


    @Override
    public void create() {
        Gdx.input.setInputProcessor(inputProcessor);
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }
}
