package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Bullet {
    private static final int SPEED = 100;

    private final float height = 32;
    private final float width = 10;
    private final float halfHeight = height / 2;
    private final float halfWidth = width / 2;
    private static Texture texture;
    private final TextureRegion textureRegion;

    private final Vector2 position = new Vector2();
    private final Vector2 angle = new Vector2();
    private final HashMap<Float, Vector2> draft = new HashMap<>();

    public boolean destroyed = false;


    public Bullet(Vector2 position, Vector2 angle, float size) {
        this.position.set(position).add(size / 2, size + 1);
        setDraft(size);
        calcPosition(angle, position);
        this.angle.set(angle);

        if (texture == null) {
            texture = new Texture("bullet.png");
        }

        this.textureRegion = new TextureRegion(texture);
    }

    private void setDraft(float size) {
        draft.put(0f, new Vector2(size / 2 - 5, size + 4));
        draft.put(45f, new Vector2(-5, size - 15));
        draft.put(90f, new Vector2(5, size / 2 - 15));
        draft.put(135f, new Vector2(0, -7));
        draft.put(180f, new Vector2(size / 2 - 5, -18));
        draft.put(225f, new Vector2(size, -17));
        draft.put(270f, new Vector2(size + 4, size / 2 - 15));
        draft.put(315f, new Vector2(size , size - 10));
        draft.put(360f, new Vector2(size / 2 - 5, size + 4));
    }

    private void calcPosition(Vector2 angle, Vector2 position) {
        this.position.set(position).add(draft.get(Math.abs(angle.angleDeg())));
    }

    public void update(float deltaTime) {
        if (angle.x == 0.0f && angle.y == 0.0f) angle.set(3, 0);
        position.add(SPEED * deltaTime * -angle.y, SPEED * deltaTime * angle.x);
        if (position.x > Gdx.graphics.getWidth()
                || position.x < 0
                || position.y > Gdx.graphics.getHeight()
                || position.y < 0) {
            destroyed = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                halfWidth,
                halfHeight,
                width,
                height,
                1,
                1,
                angle.angleDeg()
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
