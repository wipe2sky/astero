package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.Bullet;
import com.mygdx.game.entity.Explosion;
import com.mygdx.game.entity.Health;
import com.mygdx.game.entity.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mygdx.game.entity.Asteroid.MAX_ASTEROIDS_COUNT;

public class MainGameScreen implements Screen {

    private final AsterGame game;
    private final Spaceship spaceship;
    private final Health health;
    private float shootTimer;
    private List<Asteroid> asteroids;
    private List<Bullet> bullets;
    private List<Explosion> explosions;
    private BitmapFont scoreFont;
    private BitmapFont heathFont;
    private int score;
    private int healthCount;
    private GlyphLayout scoreLayout;
    private GlyphLayout healthLayout;


    public MainGameScreen(AsterGame game) {
        this.game = game;
        this.spaceship = new Spaceship();
        this.health = new Health();
        this.shootTimer = 0;
        this.healthCount = 3;
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
        this.heathFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.healthLayout = new GlyphLayout(scoreFont, String.valueOf(healthCount));
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
                if (healthCount > 1) {
                    healthCount--;
                    explosions.add(new Explosion(asteroids.get(i).getPosition().x, asteroids.get(i).getPosition().y));
                    explosions.add(new Explosion(spaceship.getPosition().x, spaceship.getPosition().y));
                    spaceship.getPosition().set((Gdx.graphics.getWidth() - spaceship.getSize()) / 2,
                            (Gdx.graphics.getHeight() - spaceship.getSize()) / 2);
                    destroyedAsteroids.add(asteroids.get(i));
                } else {
                    this.dispose();
                    game.setScreen(new GameOverScreen(game, score));
                }
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
            if (explosion.isRemoved()) {
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
        scoreFont.draw(game.batch, scoreLayout, 20, Gdx.graphics.getHeight() - 20 - scoreLayout.height);

        if (healthCount == 3){
            heathFont.setColor(Color.GREEN);
        } if (healthCount == 2){
            heathFont.setColor(Color.YELLOW);
        } else if(healthCount < 2){
            heathFont.setColor(Color.RED);
        }
        healthLayout.setText(heathFont, "x" + healthCount);
        heathFont.draw(game.batch, healthLayout, Gdx.graphics.getWidth() - healthLayout.width - 25,
                Gdx.graphics.getHeight() - 20 - healthLayout.height);


        spaceship.render(game.batch);
        asteroids.forEach(asteroid -> asteroid.render(game.batch));
        bullets.forEach(bullet -> bullet.render(game.batch));
        explosions.forEach(explosion -> explosion.render(game.batch));
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
    }
}
