package com.kurtsevich.asteroids.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kurtsevich.asteroids.AsterGame;

public class GameOverScreen implements Screen {
    private static final int BANNER_WIDTH = 500;
    private static final int BANNER_HEIGHT = 200;
    private static int highScore = 0;
    private final Texture backgroundTexture;
    private final AsterGame game;
    private final int score;
    private final Texture gameOverBanner;
    private final BitmapFont scoreFont;


    public GameOverScreen(AsterGame game, int score) {
        this.game = game;
        this.score = score;
        this.gameOverBanner = new Texture("game_over.png");
        this.scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.backgroundTexture = new Texture("background.jpg");

        if (score > highScore) highScore = score;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1);
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(gameOverBanner,
                (Gdx.graphics.getWidth() - BANNER_WIDTH) >> 1,
                ((Gdx.graphics.getHeight() - BANNER_HEIGHT) >> 1) - 15f,
                BANNER_WIDTH,
                BANNER_HEIGHT
        );

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "SCORE:" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "HIGHSCORE:" + highScore, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreLayout, 20, Gdx.graphics.getHeight() - scoreLayout.height - 15);
        scoreFont.draw(game.batch, highScoreLayout, Gdx.graphics.getWidth() - highScoreLayout.width - 20, Gdx.graphics.getHeight() - highScoreLayout.height - 15);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "TRY AGAIN");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "MAIN MENU");

        float tryAgainX = (Gdx.graphics.getWidth() - tryAgainLayout.width) / 2;
        float tryAgainY = (Gdx.graphics.getHeight() - tryAgainLayout.height) / 2 - 100;
        float mainMenuX = (Gdx.graphics.getWidth() - mainMenuLayout.width) / 2;
        float mainMenuY = (Gdx.graphics.getHeight() - mainMenuLayout.height) / 2 - 200;

        int touchX = Gdx.input.getX();
        int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (Gdx.input.isTouched()) {
            if (touchX > tryAgainX
                    && touchX < tryAgainX + tryAgainLayout.width
                    && touchY > touchY - tryAgainLayout.height
                    && touchY + tryAgainLayout.height > tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }

            if (touchX > mainMenuX
                    && touchX < mainMenuX + mainMenuLayout.width
                    && touchY > touchY - mainMenuLayout.height
                    && touchY + mainMenuLayout.height > mainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
