package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;


public class Bound {
    public static void checkBounds(Vector2 position, float size, float screenWidth, float screenHeight) {
        if (position.x < -size) {
            position.set(screenWidth + size / 2, position.y);
        } else if (position.x > screenWidth + size) {
            position.set(-32, position.y);
        }

        if (position.y < -size) {
            position.set(position.x, screenHeight + size);
        } else if (position.y > screenHeight + size) {
            position.set(position.x, -size / 2);
        }
    }
}
