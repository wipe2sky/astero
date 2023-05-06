package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Bound;

import java.util.ArrayList;
import java.util.List;

public class Asteroid {
    public static final int MAX_ASTEROIDS_COUNT = 5;
    private final int size = MathUtils.random(32, 64);
    private final int halfSize = size / 2;
    private final Vector2 speed;
    private final Vector2 position = new Vector2();
    private final Texture texture;
    private final List<Texture> textures = new ArrayList<>();
    private float rotation = MathUtils.random(0, 360);
    private final float screenWidth;
    private final float screenHeight;
    private final CollisionRect collisionRect;
    private TextureRegion textureRegion;


    public Asteroid(float x, float y) {
        setTextures();
        this.texture = textures.get(MathUtils.random(0, textures.size() - 1));
        this.textureRegion = new TextureRegion(texture);
        this.collisionRect = new CollisionRect(x, y, size, size);
        position.set(x, y);
        speed = new Vector2(MathUtils.random(-2f, 2f), MathUtils.random(-2f, 2f));
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    private void setTextures() {
        textures.add(new Texture("meteor/meteorBrown_big1.png"));
        textures.add(new Texture("meteor/meteorBrown_big2.png"));
        textures.add(new Texture("meteor/meteorBrown_big3.png"));
        textures.add(new Texture("meteor/meteorBrown_big4.png"));
        textures.add(new Texture("meteor/meteorGrey_big1.png"));
        textures.add(new Texture("meteor/meteorGrey_big2.png"));
        textures.add(new Texture("meteor/meteorGrey_big3.png"));
        textures.add(new Texture("meteor/meteorGrey_big4.png"));
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public void setSpeed(float x, float y) {
        speed.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public void render(Batch batch) {
        rotate();
        Bound.checkBounds(position, size, screenWidth, screenHeight);
        collisionRect.setPosition(position);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                halfSize,
                halfSize,
                size,
                size,
                1,
                1,
                rotation
        );
    }

    private void rotate() {
        if (rotation++ > 360) {
            rotation = 0.15f;
        } else {
            rotation += 0.15f;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo() {
        position.add(speed);
    }
}
