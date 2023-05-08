package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.Bullet;
import com.mygdx.game.entity.Explosion;
import com.mygdx.game.entity.Health;
import com.mygdx.game.entity.Score;
import com.mygdx.game.entity.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MainGameScreen implements Screen {
    private static final int MAX_ASTEROIDS_COUNT = 15;
    private static final int MAX_BULLET_COUNT = 5;
    private static final float SHOOT_WAIT_TIME = 0.3f;
    private static final int HEALTH_COUNT = 3;

    private final AsterGame game;
    private final Spaceship spaceship;
    private final Health health;
    private float shootTimer;
    private final Texture backgroundTexture;
    private final List<Asteroid> asteroids;
    private final List<Bullet> bullets;
    private final List<Explosion> explosions;
    private final Score score;


    public MainGameScreen(AsterGame game) {
        this.game = game;
        this.spaceship = new Spaceship();
        this.health = new Health(HEALTH_COUNT);
        this.asteroids = createAsteroids();
        this.bullets = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.score = new Score();

        this.backgroundTexture = new Texture("background.jpg");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        spaceship.moveTo(game.inputProcessor.getDirection());
        spaceship.rotateTo(game.inputProcessor.getDirection());

        List<Bullet> destroyedBullets = getDestroyedBullets(delta);

        List<Asteroid> destroyedAsteroids = processAsteroidsAndReturnDestroyed();

        List<Explosion> explosionsToRemove = getExplosionsToRemove(delta);

        bullets.forEach(bullet ->
                asteroids.forEach(asteroid ->
                {
                    if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {
                        destroyedBullets.add(bullet);
                        destroyedAsteroids.add(asteroid);
                        explosions.add(new Explosion(asteroid.getPosition().x, asteroid.getPosition().y));
                        score.increaseScore();
                    }
                })
        );

        asteroids.removeAll(destroyedAsteroids);
        bullets.removeAll(destroyedBullets);
        explosions.removeAll(explosionsToRemove);

        addAsteroids(MAX_ASTEROIDS_COUNT - asteroids.size());
        addBullets(delta);

        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        asteroids.forEach(asteroid -> asteroid.render(game.batch));
        bullets.forEach(bullet -> bullet.render(game.batch));
        explosions.forEach(explosion -> explosion.render(game.batch));
        spaceship.render(game.batch);
        score.render(game.batch);
        health.render(game.batch);

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
        bullets.forEach(Bullet::dispose);
        asteroids.forEach(Asteroid::dispose);
        health.dispose();
        backgroundTexture.dispose();
    }

    private List<Asteroid> createAsteroids() {
        return createAsteroids(MAX_ASTEROIDS_COUNT);
    }

    private List<Asteroid> createAsteroids(int count) {
        List<Asteroid> asters = new ArrayList<>();
        while (asters.size() <= count) {
            Asteroid asteroid = new Asteroid(MathUtils.random(Gdx.graphics.getWidth()),
                    MathUtils.random(Gdx.graphics.getHeight()));
            if (!asteroid.getCollisionRect().collidesWith(spaceship.getCollisionRect())) {
                asters.add(asteroid);
            }
        }
        return asters;
    }

    public void addAsteroids(int count) {
        asteroids.addAll(createAsteroids(count));
    }

    private List<Bullet> getDestroyedBullets(float delta) {
        return bullets.stream()
                .peek(bullet -> bullet.update(delta))
                .filter(Bullet::isDestroyed)
                .collect(Collectors.toList());
    }

    private List<Asteroid> processAsteroidsAndReturnDestroyed() {
        List<Asteroid> destroyedAsteroids = new ArrayList<>();
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).moveTo();
            if (spaceship.getCollisionRect().collidesWith(asteroids.get(i).getCollisionRect())) {
                if (health.getHealthCount() > 1) {
                    health.decreaseHealthCount();
                    explosions.add(new Explosion(asteroids.get(i).getPosition().x, asteroids.get(i).getPosition().y));
                    explosions.add(new Explosion(spaceship.getPosition().x, spaceship.getPosition().y));
                    spaceship.getPosition().set((Gdx.graphics.getWidth() - spaceship.getSize()) / 2,
                            (Gdx.graphics.getHeight() - spaceship.getSize()) / 2);
                    destroyedAsteroids.add(asteroids.get(i));
                } else {
                    this.dispose();
                    game.setScreen(new GameOverScreen(game, score.getScore()));
                }
            }
            for (int j = i + 1; j < asteroids.size(); j++) {
                if (asteroids.get(i).getCollisionRect().collidesWith(asteroids.get(j).getCollisionRect())) {
                    asteroids.get(i).setSpeed(-asteroids.get(i).getMoveDirection().x, asteroids.get(i).getMoveDirection().y);
                    asteroids.get(j).setSpeed(asteroids.get(j).getMoveDirection().x, -asteroids.get(j).getMoveDirection().y);
                }
            }
        }
        return destroyedAsteroids;
    }

    private List<Explosion> getExplosionsToRemove(float delta) {
        return explosions.stream()
                .peek(explosion -> explosion.update(delta))
                .filter(Explosion::isRemoved)
                .collect(Collectors.toList());
    }

    private void addBullets(float delta) {
        shootTimer += delta;
        if (game.inputProcessor.isSpacePressed()
                && bullets.size() < MAX_BULLET_COUNT
                && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;
            bullets.add(new Bullet(spaceship.getPosition(), spaceship.getAngle(), spaceship.getSize()));
        }
    }
}
