package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private static final float FRAME_LENGTH = 0.2f;
    private static final int SIZE = 32;
    private static final Animation<TextureRegion> ANIMATION = new Animation<>(
            FRAME_LENGTH,
            TextureRegion.split(new Texture("explosion.png"),
                    SIZE,
                    SIZE)[0]
    );
    private final Vector2 position;
    private float stateTime;
    private boolean removed;

    public Explosion(float x, float y) {
        this.position = new Vector2(x, y);
        this.stateTime = 0;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (ANIMATION.isAnimationFinished(stateTime)) {
            removed = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(ANIMATION.getKeyFrame(stateTime), position.x, position.y);
    }
}
