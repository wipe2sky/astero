package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.graphics.Texture;

public class Button extends Texture {
    private final int widthButton;
    private final int heightButton;

    public Button(int widthButton, int heightButton, String image) {
        super(image);
        this.widthButton = widthButton;
        this.heightButton = heightButton;
    }

    public int getWidthButton() {
        return widthButton;
    }

    public int getHeightButton() {
        return heightButton;
    }
}
