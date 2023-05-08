package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Bound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Asteroid {
    private final int size;
    private final Vector2 moveDirection;
    private final Vector2 position;
    private final Texture texture;
    private float rotation;
    private final CollisionRect collisionRect;
    private final TextureRegion textureRegion;


    public Asteroid(float x, float y) {
        List<Texture> textures = loadTextures();
        this.texture = textures.get(MathUtils.random(0, textures.size() - 1));
        this.textureRegion = new TextureRegion(texture);
        this.size = MathUtils.random(32, 64);
        this.collisionRect = new CollisionRect(x, y, size, size);
        this.position = new Vector2(x, y);
        this.moveDirection = new Vector2(MathUtils.random(-2f, 2f), MathUtils.random(-2f, 2f));
        this.rotation = MathUtils.random(0, 360);
    }

    public Vector2 getMoveDirection() {
        return moveDirection;
    }

    public void setSpeed(float x, float y) {
        moveDirection.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    private static ArrayList<Texture> loadTextures() {
        return new ArrayList<>(
                Arrays.asList(
                        new Texture("meteor/meteorBrown_big1.png"),
                        new Texture("meteor/meteorBrown_big2.png"),
                        new Texture("meteor/meteorBrown_big3.png"),
                        new Texture("meteor/meteorBrown_big4.png"),
                        new Texture("meteor/meteorGrey_big1.png"),
                        new Texture("meteor/meteorGrey_big2.png"),
                        new Texture("meteor/meteorGrey_big3.png"),
                        new Texture("meteor/meteorGrey_big4.png")
                ));
    }

    public void render(Batch batch) {
        rotate();
        Bound.screenExitControl(position, size);
        collisionRect.setPosition(position);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                size / 2f,
                size / 2f,
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
        position.add(moveDirection);
    }
}
