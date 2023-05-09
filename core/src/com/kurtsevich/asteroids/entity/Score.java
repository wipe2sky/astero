package com.kurtsevich.asteroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    private final BitmapFont scoreFont;
    private final GlyphLayout scoreLayout;
    private int score;


    public Score() {
        this.scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.scoreLayout = new GlyphLayout(scoreFont, String.valueOf(score));
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score += 5;
    }

    public void render(SpriteBatch batch) {
        scoreLayout.setText(scoreFont, "SCORE:" + score);
        scoreFont.draw(batch, scoreLayout, 20, Gdx.graphics.getHeight() - 20 - scoreLayout.height);
    }
}
