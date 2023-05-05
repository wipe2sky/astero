package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Button;

public class MainMenuScreen implements Screen {
    private static final int EXIT_BUTTON_Y = 200;
    private static final int PLAY_BUTTON_Y = 400;

    private final AsterGame game;
    private final Button playButtonActive;
    private final Button playButtonInactive;
    private final Button exitButtonActive;
    private final Button exitButtonInactive;

    public MainMenuScreen(AsterGame game) {
        this.game = game;
        this.playButtonActive = new Button(250, 120, "play_button_active.png");
        this.playButtonInactive = new Button(250, 120, "play_button_inactive.png");
        this.exitButtonActive = new Button(300, 120, "exit_button_active.png");
        this.exitButtonInactive = new Button(300, 120, "exit_button_inactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1);
        game.batch.begin();

        int x = AsterGame.WIDTH / 2 - exitButtonActive.getWidthButton() / 2;

        if (Gdx.input.getX() > x && Gdx.input.getX() < x + exitButtonActive.getWidthButton()
                && AsterGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y
                && AsterGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + exitButtonActive.getHeight()) {
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, exitButtonActive.getWidthButton(), exitButtonActive.getHeight());

            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, exitButtonActive.getWidthButton(), exitButtonActive.getHeight());
        }

        x = AsterGame.WIDTH / 2 - playButtonActive.getWidthButton() / 2;

        if (Gdx.input.getX() > x && Gdx.input.getX() < x + playButtonActive.getWidthButton()
                && AsterGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y
                && AsterGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + playButtonActive.getHeightButton()) {

            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, playButtonActive.getWidthButton(), playButtonActive.getHeightButton());

            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, playButtonActive.getWidthButton(), playButtonActive.getHeightButton());
        }

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
