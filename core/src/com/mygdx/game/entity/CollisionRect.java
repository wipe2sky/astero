package com.mygdx.game.entity;

import com.badlogic.gdx.math.Vector2;

public class CollisionRect {

    private final int width;
    private final int height;
    private Vector2 position;

    public CollisionRect(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition;
    }

    public boolean collidesWith(CollisionRect rect) {
        return position.x < rect.position.x + rect.width
                && position.y < rect.position.y + rect.height
                && position.x + width > rect.position.x
                && position.y + height > rect.position.y;
    }
}
