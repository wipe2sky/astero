package com.mygdx.game.entity;

import com.badlogic.gdx.math.Vector2;

public class CollisionRect {

    private final int size;
    private Vector2 position = new Vector2();

    public CollisionRect(float x, float y, int size) {
        position.set(x, y);
        this.size = size;
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition;
    }

    public boolean collidesWith(CollisionRect rect) {
        return position.x < rect.position.x + rect.size
                && position.y < rect.position.y + rect.size
                && position.x + size > rect.position.x
                && position.y + size > rect.position.y;
    }
}
