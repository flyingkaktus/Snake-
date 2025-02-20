package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Drawed when condition is met in gameScreen class.
 * @author Minh, Maciej
 */
import java.sql.SQLException;
import java.util.Collections;

import static com.mygdx.game.GameState.*;

public class EndScreen extends ScreenAdapter {

    Snake game;
    HighscoreScreen newHigh;
    NetworkClient_w newClient;

    private int width = 1440;
    private int height = 2700;
    public OrthographicCamera camera2 = new OrthographicCamera(width, height);

    public EndScreen(Snake game){
        this.game = game;
        }

    @Override
    public void show(){

        game.score_neu.manage();
        game.score_neu.listA.add(game.score_neu.getScore_latest());
        game.score_neu.sortthatlist();

        try {
            newClient = new NetworkClient_w(game.score_neu);
            newClient.TransferScore();
            newClient.getDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode){
                if(keyCode == Input.Keys.ENTER){
                    newHigh = new HighscoreScreen(game, newClient);
                    game.setScreen(newHigh);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        game.batch.setProjectionMatrix(camera2.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Game Over!", width*-0.45f, height*0.45f);
        game.font.draw(game.batch, "Score: " + game.score_neu.getScore_latest(), width*-0.45f, height*.25f);
        game.font.draw(game.batch, "Highscore: " + game.score_neu.getScore_highest(), width*-0.45f, height*.2f);
        game.font.draw(game.batch, "Press Enter for High Score!",  width*-.45f,height*.05f);
      if (game.score_neu.new_score_achieved == true) {
        game.font.draw(game.batch, "Congratulations!", width*-0.45f, height*-.25f);
        game.font.draw(game.batch, "New personal record!", width*-0.45f, height*-.30f);
      }
        //  Gdx.input.getTextInput(listener, "New", "Input Your Name: ", "Your Name", Input.OnscreenKeyboardType 1);
        game.batch.end();

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

}