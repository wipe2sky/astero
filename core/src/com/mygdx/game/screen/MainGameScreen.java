package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainGameScreen implements Screen {

    private Spaceship spaceship;

    private List<Asteroid> asteroids = new ArrayList<>();

    private AsterGame game;

    public MainGameScreen(AsterGame game) {
        this.game = game;
        spaceship = new Spaceship();
        asteroids.addAll(IntStream.range(0, 5)
                .mapToObj(i -> {
                    int x = MathUtils.random(Gdx.graphics.getWidth());
                    int y = MathUtils.random(Gdx.graphics.getHeight());
                    return new Asteroid(x, y);
                })
                .collect(Collectors.toList()));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        spaceship.moveTo(game.inputProcessor.getDirection());
        spaceship.rotateTo(game.inputProcessor.getDirection());

        asteroids.forEach(asteroid -> asteroid.moveTo());
//

        game.batch.begin();
        spaceship.render(game.batch);
        asteroids.forEach(asteroid -> asteroid.render(game.batch));
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spaceship.dispose();
        asteroids.forEach(Asteroid::dispose);
    }
}
