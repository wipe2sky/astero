package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private static final float FRAME_LENGTH = 0.2f;
    private static final int OFFSET = 8;
    private static final int SIZE = 32;

    private static Animation animation;
    private Vector2 position = new Vector2();
    private float stateTime;
    private boolean removed;

    public Explosion(float x, float y) {
        position.set(x - OFFSET, y - OFFSET);
        this.stateTime = 0;

        if (animation == null) {
            animation = new Animation<>(FRAME_LENGTH,
                    TextureRegion.split(new Texture("explosion.png"), SIZE, SIZE)[0]
            );
        }
    }

    public boolean isRemoved() {
        return removed;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)) {
            removed = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime), position.x, position.y);
    }
}
