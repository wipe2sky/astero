package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Health {
    private static final float WIDTH = 32;
    private static final float HEIGHT = 32;
    private static final float HALF_WIDTH = WIDTH / 2;
    private static final float HALF_HEIGHT = HEIGHT / 2;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Vector2 position;
    private int healthCount;

    private final BitmapFont heathFont;
    private final GlyphLayout healthLayout;


    public Health(int healthCount) {
        this.texture = new Texture("health.png");
        this.position = new Vector2().set(Gdx.graphics.getWidth() - 100 - WIDTH, Gdx.graphics.getHeight() - 40 - HEIGHT);
        this.textureRegion = new TextureRegion(texture);
        this.heathFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.healthLayout = new GlyphLayout(heathFont, String.valueOf(healthCount));
        this.healthCount = healthCount;
    }

    public int getHealthCount() {
        return healthCount;
    }

    public void decreaseHealthCount() {
        healthCount--;
    }

    public void render(SpriteBatch batch) {
        if (healthCount == 3) {
            heathFont.setColor(Color.GREEN);
        }
        if (healthCount == 2) {
            heathFont.setColor(Color.YELLOW);
        } else if (healthCount < 2) {
            heathFont.setColor(Color.RED);
        }
        healthLayout.setText(heathFont, "x" + healthCount);

        heathFont.draw(batch, healthLayout, Gdx.graphics.getWidth() - healthLayout.width - 25,
                Gdx.graphics.getHeight() - 20 - healthLayout.height);
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                HALF_WIDTH,
                HALF_HEIGHT,
                WIDTH,
                HEIGHT,
                1,
                1,
                0
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
