package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.Bullet;
import com.mygdx.game.entity.Spaceship;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainGameScreen implements Screen {

    private final AsterGame game;
    private final Spaceship spaceship;
    private List<Asteroid> asteroids;
    private Bullet bullet;

    public MainGameScreen(AsterGame game) {
        this.game = game;
        this.spaceship = new Spaceship();
        this.asteroids = IntStream.range(0, 5)
                .mapToObj(i -> {
                    int x = MathUtils.random(Gdx.graphics.getWidth());
                    int y = MathUtils.random(Gdx.graphics.getHeight());
                    return new Asteroid(x, y);
                }).collect(Collectors.toList());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        spaceship.moveTo(game.inputProcessor.getDirection());
        spaceship.rotateTo(game.inputProcessor.getDirection());

        if (bullet != null && !bullet.destroyed) {
            bullet.update(delta);
        } else if (game.inputProcessor.isSpacePressed()) {
            bullet = new Bullet(spaceship.getPosition(), spaceship.getAngle(), spaceship.getSize());
        }

        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).moveTo();
            if (spaceship.getCollisionRect().collidesWith(asteroids.get(i).getCollisionRect())) {
                game.setScreen(new MainMenuScreen(game));
            }
            for (int j = i + 1; j < asteroids.size(); j++) {
                if (asteroids.get(i).getCollisionRect().collidesWith(asteroids.get(j).getCollisionRect())) {
                    asteroids.get(i).setSpeed(-asteroids.get(i).getSpeed().x, asteroids.get(i).getSpeed().y);
                    asteroids.get(j).setSpeed(asteroids.get(j).getSpeed().x, -asteroids.get(j).getSpeed().y);
                }
            }
        }

        game.batch.begin();
        spaceship.render(game.batch);
        asteroids.forEach(asteroid -> asteroid.render(game.batch));
        if (bullet != null && !bullet.destroyed) bullet.render(game.batch);
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
        bullet.dispose();
        asteroids.forEach(Asteroid::dispose);
    }
}
