package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.Bullet;
import com.mygdx.game.entity.Explosion;
import com.mygdx.game.entity.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mygdx.game.entity.Asteroid.MAX_ASTEROIDS_COUNT;

public class MainGameScreen implements Screen {

    private final AsterGame game;
    private final Spaceship spaceship;
    private float shootTimer;
    private List<Asteroid> asteroids;
    private List<Bullet> bullets;
    private List<Explosion> explosions;
    private BitmapFont scoreFont;
    private int score;
    GlyphLayout scoreLayout;


    public MainGameScreen(AsterGame game) {
        this.game = game;
        this.spaceship = new Spaceship();
        this.shootTimer = 0;
        this.asteroids = IntStream.range(0, MAX_ASTEROIDS_COUNT)
                .mapToObj(i -> {
                    int x = MathUtils.random(Gdx.graphics.getWidth());
                    int y = MathUtils.random(Gdx.graphics.getHeight());
                    return new Asteroid(x, y);
                }).collect(Collectors.toList());
        this.bullets = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.scoreLayout = new GlyphLayout(scoreFont, String.valueOf(score));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        spaceship.moveTo(game.inputProcessor.getDirection());
        spaceship.rotateTo(game.inputProcessor.getDirection());

        List<Bullet> destroyedBullets = new ArrayList<>();
        bullets.forEach(bullet -> {
            bullet.update(delta);
            if (bullet.isDestroyed()) {
                destroyedBullets.add(bullet);
            }
        });


        List<Asteroid> destroyedAsteroids = new ArrayList<>();
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

        List<Explosion> explosionsToRemove = new ArrayList<>();
        explosions.forEach(explosion -> {
            explosion.update(delta);
            if(explosion.isRemoved()){
                explosionsToRemove.add(explosion);
            }
        });

        bullets.forEach(bullet -> {
            asteroids.forEach(asteroid ->
            {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {
                    destroyedBullets.add(bullet);
                    destroyedAsteroids.add(asteroid);
                    explosions.add(new Explosion(asteroid.getPosition().x, asteroid.getPosition().y));
                    score += 5;
                }
            });
        });

        asteroids.removeAll(destroyedAsteroids);
        bullets.removeAll(destroyedBullets);
        explosions.removeAll(explosionsToRemove);

        while (asteroids.size() < MAX_ASTEROIDS_COUNT) {
            asteroids.add(new Asteroid(MathUtils.random(Gdx.graphics.getWidth()),
                    MathUtils.random(Gdx.graphics.getWidth())));
        }


        shootTimer += delta;
        if (game.inputProcessor.isSpacePressed()
                && bullets.size() < Bullet.MAX_COUNT
                && shootTimer >= Bullet.SHOOT_WAIT_TIME) {
            shootTimer = 0;
            bullets.add(new Bullet(spaceship.getPosition(), spaceship.getAngle(), spaceship.getSize()));
        }


        game.batch.begin();
        scoreLayout.setText(scoreFont, "SCORE:" + score);
        scoreFont.draw(game.batch, scoreLayout, 40, Gdx.graphics.getHeight() - 20 - scoreLayout.height);
        spaceship.render(game.batch);
        asteroids.forEach(asteroid -> asteroid.render(game.batch));
        bullets.forEach(bullet -> bullet.render(game.batch));
        explosions.forEach(explosion -> explosion.render(game.batch));
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
    }
}
