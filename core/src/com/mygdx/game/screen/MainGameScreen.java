package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.AsterGame;
import com.mygdx.game.entity.Spaceship;

public class MainGameScreen implements Screen {

    private Spaceship spaceship;

    private AsterGame game;

    public MainGameScreen(AsterGame game) {
        this.game = game;
        spaceship = new Spaceship();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        spaceship.moveTo(game.inputProcessor.getDirection());
        spaceship.rotateTo(game.inputProcessor.getDirection());
//

        game.batch.begin();
        spaceship.render(game.batch);
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
//        game.batch.dispose();
        spaceship.dispose();
    }
}
