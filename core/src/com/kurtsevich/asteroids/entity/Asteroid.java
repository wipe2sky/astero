package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kurtsevich.asteroids.utils.Bound;

import java.util.ArrayList;
import java.util.Arrays;

public class Asteroid {
    private final int size;
    private final int speed;
    private final Vector2 position;
    private final Vector2 nextPosition;
    private final Texture texture;
    private float angle;
    private float rotationAngle;
    private final CollisionRect collisionRect;
    private final TextureRegion textureRegion;


    public Asteroid(float x, float y) {
        this.texture = loadTextures().get(MathUtils.random(0, loadTextures().size() - 1));
        this.speed = MathUtils.random(50, 150);
        this.angle = MathUtils.random(0, 360);
        this.rotationAngle = angle;
        this.textureRegion = new TextureRegion(texture);
        this.size = MathUtils.random(32, 64);
        this.collisionRect = new CollisionRect(x, y, size, size);
        this.position = new Vector2(x, y);
        this.nextPosition = new Vector2(1, 1);
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


    public void reflectAngle() {
        angle = Math.abs(angle - 360);
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
                rotationAngle
        );
    }

    private void rotate() {
        if (rotationAngle++ > 360) {
            rotationAngle = 0.15f;
        } else {
            rotationAngle += 0.15f;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public void moveTo(float deltaTime) {
        float stepLength = speed * deltaTime;
        nextPosition.setLength(stepLength);
        nextPosition.setAngleDeg(angle);

        position.add(nextPosition);
        collisionRect.setPosition(position);
    }
}
